package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgriPlanEntity
import com.example.data.model.MilestoneStatus
import com.example.data.model.TimelineMilestone
import com.example.ui.theme.AgriAmberSecondary
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.viewmodel.AgriNavTab

@Composable
fun DashboardScreen(
    plan: AgriPlanEntity?,
    allPlans: List<AgriPlanEntity>,
    milestones: List<TimelineMilestone>,
    totalSources: Int,
    totalMetrics: Int,
    onTabSelected: (AgriNavTab) -> Unit,
    onSelectPlan: (AgriPlanEntity) -> Unit,
    onCreateNewPlanClick: () -> Unit
) {
    if (plan == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularLoadingOrEmpty(onCreateNewPlanClick)
        }
        return
    }

    val selectedSourcesCount = plan.selectedSourceIds.split(",").count { it.isNotBlank() }
    val selectedMetricsCount = plan.selectedMetricIds.split(",").count { it.isNotBlank() }
    val completedMilestones = milestones.count { it.status == MilestoneStatus.COMPLETED }
    val milestoneProgress = if (milestones.isNotEmpty()) completedMilestones.toFloat() / milestones.size.toFloat() else 0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Active Plan Card
        item {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_plan_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = plan.sector,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            color = when (plan.status) {
                                "Ready for Submission" -> Color(0xFF2E7D32)
                                "In Review" -> Color(0xFFE65100)
                                else -> MaterialTheme.colorScheme.secondary
                            },
                            shape = CircleShape
                        ) {
                            Text(
                                text = plan.status,
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = plan.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Focus: ${plan.targetCommodities}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Researcher: ${plan.studentAuthor} • ${plan.institution}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onTabSelected(AgriNavTab.DOC_DELIVERABLE) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.weight(1f).testTag("btn_view_doc_hero")
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("View Plan DOC", fontSize = 13.sp)
                        }

                        FilledTonalButton(
                            onClick = { onTabSelected(AgriNavTab.PLAN_BUILDER) },
                            modifier = Modifier.weight(1f).testTag("btn_edit_plan_hero")
                        ) {
                            Text("Edit Strategy", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Analytical Readiness Grid
        item {
            Text(
                text = "Analytical Architecture Progress",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricProgressCard(
                    title = "Public Sources",
                    value = "$selectedSourcesCount / $totalSources",
                    subtitle = "Open Data Feeds",
                    icon = Icons.Default.CloudDownload,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected(AgriNavTab.DATA_SOURCES) }
                )

                MetricProgressCard(
                    title = "Agri Indicators",
                    value = "$selectedMetricsCount / $totalMetrics",
                    subtitle = "Yields & Pricing",
                    icon = Icons.Default.BarChart,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected(AgriNavTab.METRICS) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricProgressCard(
                    title = "Pipeline Stages",
                    value = "6 / 6",
                    subtitle = "ETL & ML Ready",
                    icon = Icons.Default.Schema,
                    color = Color(0xFF00796B),
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected(AgriNavTab.PIPELINE) }
                )

                MetricProgressCard(
                    title = "Milestone Gantt",
                    value = "${(milestoneProgress * 100).toInt()}%",
                    subtitle = "$completedMilestones of ${milestones.size} Done",
                    icon = Icons.Default.DateRange,
                    color = Color(0xFF6A1B9A),
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected(AgriNavTab.TIMELINE_GANTT) }
                )
            }
        }

        // Objective & Deliverables Requirement Checklist
        item {
            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Agribusiness Project Deliverable Checklist",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ChecklistItem(
                        number = "1",
                        title = "Research Strategy & Rationale",
                        desc = "Outlines market trend problem statement, hypotheses, and scope.",
                        isComplete = plan.researchRationale.isNotBlank()
                    )
                    ChecklistItem(
                        number = "2",
                        title = "Public Agribusiness Data Sources",
                        desc = "Identifies USDA, FAOSTAT, World Bank, and Copernicus open APIs.",
                        isComplete = selectedSourcesCount >= 3
                    )
                    ChecklistItem(
                        number = "3",
                        title = "Key Indicators (Yield, Price, Resources)",
                        desc = "Formulas, measurement units, and agribusiness market impact.",
                        isComplete = selectedMetricsCount >= 3
                    )
                    ChecklistItem(
                        number = "4",
                        title = "Step-by-Step Analytical Pipeline",
                        desc = "Collection, cleaning, imputation, econometric modeling, and ML.",
                        isComplete = true
                    )
                    ChecklistItem(
                        number = "5",
                        title = "Milestone Timeline & Gantt Schedule",
                        desc = "Structured ${plan.totalWeeks}-week execution schedule with deliverables.",
                        isComplete = milestones.isNotEmpty()
                    )
                    ChecklistItem(
                        number = "6",
                        title = "Comprehensive DOC Deliverable",
                        desc = "Exportable structured report with headings, subheadings, and bullets.",
                        isComplete = true
                    )
                }
            }
        }

        // Quick Navigation Launchers
        item {
            Text(
                text = "Planning Studio Modules",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ModuleLauncherCard(
                title = "1. Public Data Sources Directory",
                desc = "Explore USDA NASS, FAOSTAT, World Bank Pink Sheet, Copernicus AgERA5, CME futures.",
                icon = Icons.Default.CloudDownload,
                tag = "10 Public Feeds",
                onClick = { onTabSelected(AgriNavTab.DATA_SOURCES) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ModuleLauncherCard(
                title = "2. Key Indicators & Metrics Taxonomy",
                desc = "Crop yields, cash-futures basis, fertilizer intensity, stocks-to-use ratios.",
                icon = Icons.Default.BarChart,
                tag = "14 Formulas",
                onClick = { onTabSelected(AgriNavTab.METRICS) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ModuleLauncherCard(
                title = "3. End-to-End Analytical Pipeline",
                desc = "ETL ingestion, outlier handling, STL seasonal decomposition, ARIMA, and XGBoost.",
                icon = Icons.Default.Schema,
                tag = "6 Phases",
                onClick = { onTabSelected(AgriNavTab.PIPELINE) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ModuleLauncherCard(
                title = "4. Timeline & Gantt Schedule",
                desc = "Track ${plan.totalWeeks}-week phases, milestone deliverables, and analytical risk buffers.",
                icon = Icons.Default.DateRange,
                tag = "${milestones.size} Milestones",
                onClick = { onTabSelected(AgriNavTab.TIMELINE_GANTT) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ModuleLauncherCard(
                title = "5. Methodological Rubric Auditor",
                desc = "Benchmark against evaluation criteria: clarity, depth, rigor, feasibility, readability.",
                icon = Icons.Default.Grade,
                tag = "5 Rubric Criteria",
                onClick = { onTabSelected(AgriNavTab.RUBRIC_AUDIT) }
            )
        }

        // Switch or Create Plans Section
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Available Pre-loaded Research Plans",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    allPlans.forEach { p ->
                        val isSelected = p.id == plan.id
                        Surface(
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onSelectPlan(p) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = p.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    )
                                    Text(
                                        text = "${p.sector} • ${p.targetCommodities}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Active",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onCreateNewPlanClick,
                        modifier = Modifier.fillMaxWidth().testTag("btn_create_plan_dashboard")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Create Custom Agribusiness Plan")
                    }
                }
            }
        }
    }
}

@Composable
fun MetricProgressCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ChecklistItem(
    number: String,
    title: String,
    desc: String,
    isComplete: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(
                    if (isComplete) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = if (isComplete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                if (isComplete) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Complete",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ModuleLauncherCard(
    title: String,
    desc: String,
    icon: ImageVector,
    tag: String,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = tag,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun CircularLoadingOrEmpty(onCreateClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(24.dp)
    ) {
        Text("No active research plan found", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onCreateClick) {
            Text("Create New Agribusiness Plan")
        }
    }
}
