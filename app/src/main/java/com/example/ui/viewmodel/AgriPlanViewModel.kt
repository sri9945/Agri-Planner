package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AgriDatabase
import com.example.data.model.AgriMetric
import com.example.data.model.AgriPlanEntity
import com.example.data.model.DataSource
import com.example.data.model.MilestoneStatus
import com.example.data.model.PipelineStage
import com.example.data.model.RubricCriteria
import com.example.data.model.TimelineMilestone
import com.example.data.repository.AgriPlanRepository
import com.example.util.DocGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

enum class AgriNavTab(val label: String, val testTag: String) {
    DASHBOARD("Overview", "tab_dashboard"),
    PLAN_BUILDER("Plan Design", "tab_plan_builder"),
    DATA_SOURCES("Data Sources", "tab_data_sources"),
    METRICS("Indicators", "tab_metrics"),
    PIPELINE("Data Pipeline", "tab_pipeline"),
    TIMELINE_GANTT("Timeline & Gantt", "tab_timeline"),
    RUBRIC_AUDIT("Evaluation Rubric", "tab_rubric"),
    DOC_DELIVERABLE("DOC Deliverable", "tab_deliverable")
}

class AgriPlanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AgriPlanRepository

    val allPlans: StateFlow<List<AgriPlanEntity>>

    private val _activePlan = MutableStateFlow<AgriPlanEntity?>(null)
    val activePlan: StateFlow<AgriPlanEntity?> = _activePlan.asStateFlow()

    private val _currentTab = MutableStateFlow(AgriNavTab.DASHBOARD)
    val currentTab: StateFlow<AgriNavTab> = _currentTab.asStateFlow()

    // Catalogs
    val allSources: List<DataSource>
    val allMetrics: List<AgriMetric>
    val allPipelineStages: List<PipelineStage>

    // Dynamic Milestones & Rubric
    private val _milestones = MutableStateFlow<List<TimelineMilestone>>(emptyList())
    val milestones: StateFlow<List<TimelineMilestone>> = _milestones.asStateFlow()

    private val _rubricCriteria = MutableStateFlow<List<RubricCriteria>>(emptyList())
    val rubricCriteria: StateFlow<List<RubricCriteria>> = _rubricCriteria.asStateFlow()

    // Filters
    private val _sourceSearchQuery = MutableStateFlow("")
    val sourceSearchQuery: StateFlow<String> = _sourceSearchQuery.asStateFlow()

    private val _sourceDomainFilter = MutableStateFlow("All")
    val sourceDomainFilter: StateFlow<String> = _sourceDomainFilter.asStateFlow()

    private val _metricSearchQuery = MutableStateFlow("")
    val metricSearchQuery: StateFlow<String> = _metricSearchQuery.asStateFlow()

    private val _metricCategoryFilter = MutableStateFlow("All")
    val metricCategoryFilter: StateFlow<String> = _metricCategoryFilter.asStateFlow()

    // Inspection dialogs
    private val _inspectingSource = MutableStateFlow<DataSource?>(null)
    val inspectingSource: StateFlow<DataSource?> = _inspectingSource.asStateFlow()

    private val _inspectingMetric = MutableStateFlow<AgriMetric?>(null)
    val inspectingMetric: StateFlow<AgriMetric?> = _inspectingMetric.asStateFlow()

    init {
        val db = AgriDatabase.getDatabase(application)
        repository = AgriPlanRepository(db.agriPlanDao())

        allSources = repository.getAllDataSources()
        allMetrics = repository.getAllMetrics()
        allPipelineStages = repository.getAllPipelineStages()
        _milestones.value = repository.getDefaultMilestones()
        _rubricCriteria.value = repository.getRubricCriteriaList()

        allPlans = repository.allPlans.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.initializePrepopulatedDataIfNeeded()
            // Observe plans and select first if none selected
            repository.allPlans.collect { plans ->
                if (_activePlan.value == null && plans.isNotEmpty()) {
                    _activePlan.value = plans.first()
                } else if (_activePlan.value != null) {
                    val updated = plans.find { it.id == _activePlan.value?.id }
                    if (updated != null) {
                        _activePlan.value = updated
                    }
                }
            }
        }
    }

    fun setTab(tab: AgriNavTab) {
        _currentTab.value = tab
    }

    fun selectPlan(plan: AgriPlanEntity) {
        _activePlan.value = plan
    }

    fun updateActivePlan(updated: AgriPlanEntity) {
        _activePlan.value = updated
        viewModelScope.launch {
            repository.savePlan(updated)
        }
    }

    fun toggleSourceInPlan(sourceId: String) {
        val current = _activePlan.value ?: return
        val currentIds = current.selectedSourceIds.split(",").filter { it.isNotBlank() }.toMutableList()
        if (currentIds.contains(sourceId)) {
            currentIds.remove(sourceId)
        } else {
            currentIds.add(sourceId)
        }
        val updated = current.copy(selectedSourceIds = currentIds.joinToString(","))
        updateActivePlan(updated)
    }

    fun toggleMetricInPlan(metricId: String) {
        val current = _activePlan.value ?: return
        val currentIds = current.selectedMetricIds.split(",").filter { it.isNotBlank() }.toMutableList()
        if (currentIds.contains(metricId)) {
            currentIds.remove(metricId)
        } else {
            currentIds.add(metricId)
        }
        val updated = current.copy(selectedMetricIds = currentIds.joinToString(","))
        updateActivePlan(updated)
    }

    fun toggleMilestoneStatus(milestoneId: String) {
        val currentList = _milestones.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == milestoneId }
        if (index != -1) {
            val item = currentList[index]
            val nextStatus = when (item.status) {
                MilestoneStatus.PENDING -> MilestoneStatus.IN_PROGRESS
                MilestoneStatus.IN_PROGRESS -> MilestoneStatus.COMPLETED
                MilestoneStatus.COMPLETED -> MilestoneStatus.PENDING
            }
            currentList[index] = item.copy(status = nextStatus)
            _milestones.value = currentList
        }
    }

    fun updateRubricScore(criterionId: String, newScore: Int) {
        val currentList = _rubricCriteria.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == criterionId }
        if (index != -1) {
            val item = currentList[index]
            val clamped = newScore.coerceIn(0, item.maxScore)
            currentList[index] = item.copy(currentScore = clamped)
            _rubricCriteria.value = currentList
        }
    }

    fun createNewPlan(
        title: String,
        sector: String,
        targetCommodities: String,
        author: String,
        institution: String
    ) {
        viewModelScope.launch {
            val newPlan = AgriPlanEntity(
                title = title.ifBlank { "Custom Agribusiness Analysis Plan" },
                sector = sector.ifBlank { "Grain & Oilseed Agribusiness" },
                targetCommodities = targetCommodities.ifBlank { "Corn, Soybeans, Wheat" },
                studentAuthor = author.ifBlank { "Agribusiness Analyst" },
                institution = institution.ifBlank { "Agricultural Economics Institute" },
                submissionDate = "October 2026",
                executiveSummary = "This data analysis plan outlines a strategy for systematically monitoring public agricultural repositories, processing indicators, and generating market forecasts.",
                researchRationale = "Public agricultural data provides transparent intelligence for stabilizing farm prices, mitigating weather risks, and enhancing supply chain efficiency.",
                problemStatement = "How can open-access market and agronomic data be transformed into predictive indicators of price volatility?",
                researchQuestions = "1. What is the impact of weather stress on regional crop yields?\n2. How do input costs affect farm gate commodity prices?",
                selectedSourceIds = "usda_nass,faostat,worldbank_pinksheet",
                selectedMetricIds = "metric_yield_per_ha,metric_cash_futures_basis,metric_fertilizer_intensity",
                totalWeeks = 14,
                status = "Draft",
                customNotes = "New student project plan",
                isPreloaded = false
            )
            val newId = repository.savePlan(newPlan)
            _activePlan.value = newPlan.copy(id = newId)
        }
    }

    fun deleteCurrentPlan() {
        val current = _activePlan.value ?: return
        viewModelScope.launch {
            repository.deletePlan(current.id)
            _activePlan.value = null
        }
    }

    fun setSourceSearch(query: String) {
        _sourceSearchQuery.value = query
    }

    fun setSourceDomainFilter(domain: String) {
        _sourceDomainFilter.value = domain
    }

    fun setMetricSearch(query: String) {
        _metricSearchQuery.value = query
    }

    fun setMetricCategoryFilter(cat: String) {
        _metricCategoryFilter.value = cat
    }

    fun inspectSource(source: DataSource?) {
        _inspectingSource.value = source
    }

    fun inspectMetric(metric: AgriMetric?) {
        _inspectingMetric.value = metric
    }

    fun getFullDocumentText(): String {
        val plan = _activePlan.value ?: return "No plan selected."
        return DocGenerator.generateFullPlanDocument(
            plan = plan,
            sources = allSources,
            metrics = allMetrics,
            stages = allPipelineStages,
            milestones = _milestones.value,
            rubricCriteria = _rubricCriteria.value
        )
    }

    fun sharePlanDocument(context: Context) {
        val plan = _activePlan.value ?: return
        val docText = getFullDocumentText()

        try {
            // Write to a temporary file in cacheDir
            val filename = "${plan.title.replace("[^a-zA-Z0-9]".toRegex(), "_")}_Agribusiness_Plan.doc"
            val file = File(context.cacheDir, filename)
            file.writeText(docText)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "${plan.title} - Agribusiness Data Analysis Plan")
                putExtra(Intent.EXTRA_TEXT, docText)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Export Agribusiness Plan Document"))
        } catch (e: Exception) {
            Toast.makeText(context, "Export error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
