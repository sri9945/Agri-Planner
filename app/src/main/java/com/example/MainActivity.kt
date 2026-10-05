package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AgriBottomNavigation
import com.example.ui.components.AgriTopBar
import com.example.ui.components.CreatePlanDialog
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DataSourcesScreen
import com.example.ui.screens.DocExportScreen
import com.example.ui.screens.MetricsCatalogScreen
import com.example.ui.screens.PipelineLabScreen
import com.example.ui.screens.PlanBuilderScreen
import com.example.ui.screens.RubricAuditScreen
import com.example.ui.screens.TimelineGanttScreen
import com.example.ui.theme.AgriPlanTheme
import com.example.ui.viewmodel.AgriNavTab
import com.example.ui.viewmodel.AgriPlanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgriPlanTheme {
                AgriDataPlannerApp()
            }
        }
    }
}

@Composable
fun AgriDataPlannerApp(
    viewModel: AgriPlanViewModel = viewModel()
) {
    val context = LocalContext.current

    val allPlans by viewModel.allPlans.collectAsStateWithLifecycle()
    val activePlan by viewModel.activePlan.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val milestones by viewModel.milestones.collectAsStateWithLifecycle()
    val rubricCriteria by viewModel.rubricCriteria.collectAsStateWithLifecycle()

    val sourceSearchQuery by viewModel.sourceSearchQuery.collectAsStateWithLifecycle()
    val sourceDomainFilter by viewModel.sourceDomainFilter.collectAsStateWithLifecycle()
    val metricSearchQuery by viewModel.metricSearchQuery.collectAsStateWithLifecycle()
    val metricCategoryFilter by viewModel.metricCategoryFilter.collectAsStateWithLifecycle()

    var showCreateDialog by remember { mutableStateOf(false) }

    // Android back handler: pop to Dashboard if on a secondary tab
    BackHandler(enabled = currentTab != AgriNavTab.DASHBOARD) {
        viewModel.setTab(AgriNavTab.DASHBOARD)
    }

    Scaffold(
        topBar = {
            AgriTopBar(
                activePlan = activePlan,
                allPlans = allPlans,
                onSelectPlan = { viewModel.selectPlan(it) },
                onExportClick = { viewModel.sharePlanDocument(context) },
                onTabSelected = { viewModel.setTab(it) }
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                currentTab = currentTab,
                onTabSelected = { viewModel.setTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AgriNavTab.DASHBOARD -> {
                    DashboardScreen(
                        plan = activePlan,
                        allPlans = allPlans,
                        milestones = milestones,
                        totalSources = viewModel.allSources.size,
                        totalMetrics = viewModel.allMetrics.size,
                        onTabSelected = { viewModel.setTab(it) },
                        onSelectPlan = { viewModel.selectPlan(it) },
                        onCreateNewPlanClick = { showCreateDialog = true }
                    )
                }

                AgriNavTab.PLAN_BUILDER -> {
                    PlanBuilderScreen(
                        plan = activePlan,
                        onSavePlan = { viewModel.updateActivePlan(it) },
                        onTabSelected = { viewModel.setTab(it) }
                    )
                }

                AgriNavTab.DATA_SOURCES -> {
                    DataSourcesScreen(
                        plan = activePlan,
                        allSources = viewModel.allSources,
                        searchQuery = sourceSearchQuery,
                        domainFilter = sourceDomainFilter,
                        onSearchChange = { viewModel.setSourceSearch(it) },
                        onDomainFilterChange = { viewModel.setSourceDomainFilter(it) },
                        onToggleSourceInPlan = { viewModel.toggleSourceInPlan(it) }
                    )
                }

                AgriNavTab.METRICS -> {
                    MetricsCatalogScreen(
                        plan = activePlan,
                        allMetrics = viewModel.allMetrics,
                        searchQuery = metricSearchQuery,
                        categoryFilter = metricCategoryFilter,
                        onSearchChange = { viewModel.setMetricSearch(it) },
                        onCategoryFilterChange = { viewModel.setMetricCategoryFilter(it) },
                        onToggleMetricInPlan = { viewModel.toggleMetricInPlan(it) }
                    )
                }

                AgriNavTab.PIPELINE -> {
                    PipelineLabScreen(
                        stages = viewModel.allPipelineStages
                    )
                }

                AgriNavTab.TIMELINE_GANTT -> {
                    TimelineGanttScreen(
                        plan = activePlan,
                        milestones = milestones,
                        onToggleMilestoneStatus = { viewModel.toggleMilestoneStatus(it) }
                    )
                }

                AgriNavTab.RUBRIC_AUDIT -> {
                    RubricAuditScreen(
                        rubricList = rubricCriteria,
                        onUpdateScore = { id, score -> viewModel.updateRubricScore(id, score) }
                    )
                }

                AgriNavTab.DOC_DELIVERABLE -> {
                    DocExportScreen(
                        plan = activePlan,
                        documentText = viewModel.getFullDocumentText(),
                        onShareDocClick = { viewModel.sharePlanDocument(context) }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        CreatePlanDialog(
            onDismiss = { showCreateDialog = false },
            onCreatePlan = { title, sector, commodities, author, institution ->
                viewModel.createNewPlan(title, sector, commodities, author, institution)
            }
        )
    }
}
