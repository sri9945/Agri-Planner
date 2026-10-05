package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgriPlanEntity
import com.example.ui.viewmodel.AgriNavTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanBuilderScreen(
    plan: AgriPlanEntity?,
    onSavePlan: (AgriPlanEntity) -> Unit,
    onTabSelected: (AgriNavTab) -> Unit
) {
    if (plan == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No active plan to edit.")
        }
        return
    }

    val context = LocalContext.current

    var title by remember(plan) { mutableStateOf(plan.title) }
    var sector by remember(plan) { mutableStateOf(plan.sector) }
    var targetCommodities by remember(plan) { mutableStateOf(plan.targetCommodities) }
    var studentAuthor by remember(plan) { mutableStateOf(plan.studentAuthor) }
    var institution by remember(plan) { mutableStateOf(plan.institution) }
    var submissionDate by remember(plan) { mutableStateOf(plan.submissionDate) }
    var status by remember(plan) { mutableStateOf(plan.status) }
    var totalWeeks by remember(plan) { mutableFloatStateOf(plan.totalWeeks.toFloat()) }
    var executiveSummary by remember(plan) { mutableStateOf(plan.executiveSummary) }
    var researchRationale by remember(plan) { mutableStateOf(plan.researchRationale) }
    var problemStatement by remember(plan) { mutableStateOf(plan.problemStatement) }
    var researchQuestions by remember(plan) { mutableStateOf(plan.researchQuestions) }

    var statusDropdownExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Draft", "In Review", "Ready for Submission")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("plan_builder_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Agribusiness Plan Architecture",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Design research rationale, scope, indicators, and timeline",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = {
                        val updated = plan.copy(
                            title = title,
                            sector = sector,
                            targetCommodities = targetCommodities,
                            studentAuthor = studentAuthor,
                            institution = institution,
                            submissionDate = submissionDate,
                            status = status,
                            totalWeeks = totalWeeks.toInt(),
                            executiveSummary = executiveSummary,
                            researchRationale = researchRationale,
                            problemStatement = problemStatement,
                            researchQuestions = researchQuestions
                        )
                        onSavePlan(updated)
                        Toast.makeText(context, "Research Plan Saved Successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("btn_save_plan_top")
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Plan")
                }
            }
        }

        // Metadata Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "1. Document Metadata & Scope",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Project / Research Title") },
                        modifier = Modifier.fillMaxWidth().testTag("input_plan_title")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = sector,
                            onValueChange = { sector = it },
                            label = { Text("Agribusiness Sector") },
                            modifier = Modifier.weight(1f).testTag("input_plan_sector")
                        )

                        OutlinedTextField(
                            value = targetCommodities,
                            onValueChange = { targetCommodities = it },
                            label = { Text("Target Commodities") },
                            modifier = Modifier.weight(1f).testTag("input_plan_commodities")
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = studentAuthor,
                            onValueChange = { studentAuthor = it },
                            label = { Text("Author / Student Fellow") },
                            modifier = Modifier.weight(1f).testTag("input_plan_author")
                        )

                        OutlinedTextField(
                            value = institution,
                            onValueChange = { institution = it },
                            label = { Text("Institution / Organization") },
                            modifier = Modifier.weight(1f).testTag("input_plan_institution")
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = submissionDate,
                            onValueChange = { submissionDate = it },
                            label = { Text("Submission Date") },
                            modifier = Modifier.weight(1f).testTag("input_plan_date")
                        )

                        ExposedDropdownMenuBox(
                            expanded = statusDropdownExpanded,
                            onExpandedChange = { statusDropdownExpanded = !statusDropdownExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = status,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Review Status") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusDropdownExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth().testTag("select_plan_status")
                            )

                            ExposedDropdownMenu(
                                expanded = statusDropdownExpanded,
                                onDismissRequest = { statusDropdownExpanded = false }
                            ) {
                                statusOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt) },
                                        onClick = {
                                            status = opt
                                            statusDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Project Duration Slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Planned Execution Duration:",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "${totalWeeks.toInt()} Weeks",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Slider(
                            value = totalWeeks,
                            onValueChange = { totalWeeks = it },
                            valueRange = 8f..24f,
                            steps = 15,
                            modifier = Modifier.fillMaxWidth().testTag("slider_plan_weeks")
                        )
                    }
                }
            }
        }

        // Executive Summary Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "2. Executive Summary",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "High-level distillation of research objective, public data sources, and anticipated market intelligence deliverables.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = executiveSummary,
                        onValueChange = { executiveSummary = it },
                        modifier = Modifier.fillMaxWidth().testTag("input_executive_summary"),
                        minLines = 4,
                        maxLines = 8
                    )
                }
            }
        }

        // Research Rationale & Problem Statement
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "3. Research Rationale & Problem Statement",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Research Rationale:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    OutlinedTextField(
                        value = researchRationale,
                        onValueChange = { researchRationale = it },
                        placeholder = { Text("Describe the economic and agricultural rationale for this analysis...") },
                        modifier = Modifier.fillMaxWidth().testTag("input_research_rationale"),
                        minLines = 4,
                        maxLines = 8
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Core Agribusiness Problem Statement:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    OutlinedTextField(
                        value = problemStatement,
                        onValueChange = { problemStatement = it },
                        placeholder = { Text("What specific market question or volatility risk does this plan address?") },
                        modifier = Modifier.fillMaxWidth().testTag("input_problem_statement"),
                        minLines = 3,
                        maxLines = 6
                    )
                }
            }
        }

        // Key Research Questions Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "4. Analytical Research Questions",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Hypotheses and empirical questions guiding the indicator selection and econometric modeling (one per line).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = researchQuestions,
                        onValueChange = { researchQuestions = it },
                        modifier = Modifier.fillMaxWidth().testTag("input_research_questions"),
                        minLines = 4,
                        maxLines = 8
                    )
                }
            }
        }

        // Next Action Shortcuts
        item {
            OutlinedCard(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Continue Plan Development:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onTabSelected(AgriNavTab.DATA_SOURCES) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Attach Data Sources", fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = { onTabSelected(AgriNavTab.METRICS) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Select Indicators", fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val updated = plan.copy(
                                title = title,
                                sector = sector,
                                targetCommodities = targetCommodities,
                                studentAuthor = studentAuthor,
                                institution = institution,
                                submissionDate = submissionDate,
                                status = status,
                                totalWeeks = totalWeeks.toInt(),
                                executiveSummary = executiveSummary,
                                researchRationale = researchRationale,
                                problemStatement = problemStatement,
                                researchQuestions = researchQuestions
                            )
                            onSavePlan(updated)
                            onTabSelected(AgriNavTab.DOC_DELIVERABLE)
                        },
                        modifier = Modifier.fillMaxWidth().testTag("btn_save_and_preview_doc")
                    ) {
                        Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save & Generate Comprehensive DOC Deliverable")
                    }
                }
            }
        }
    }
}
