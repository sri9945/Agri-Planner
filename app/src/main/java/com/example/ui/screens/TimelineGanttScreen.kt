package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgriPlanEntity
import com.example.data.model.MilestoneStatus
import com.example.data.model.TimelineMilestone

@Composable
fun TimelineGanttScreen(
    plan: AgriPlanEntity?,
    milestones: List<TimelineMilestone>,
    onToggleMilestoneStatus: (String) -> Unit
) {
    val totalPlanWeeks = plan?.totalWeeks ?: 14
    val completedCount = milestones.count { it.status == MilestoneStatus.COMPLETED }
    val inProgressCount = milestones.count { it.status == MilestoneStatus.IN_PROGRESS }
    val progressFraction = if (milestones.isNotEmpty()) completedCount.toFloat() / milestones.size.toFloat() else 0f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("timeline_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Project Timeline & Gantt Schedule",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "14-Week execution roadmap with phase milestones, deliverables, and risk buffers",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Progress Overview Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Execution Progress",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        Text(
                            text = "${(progressFraction * 100).toInt()}% Complete",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedCount Completed • $inProgressCount Active • ${milestones.size - completedCount - inProgressCount} Pending",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "Total Horizon: $totalPlanWeeks Wks",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        // Gantt Chart Visual Header & Grid
        item {
            OutlinedCard(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Visual Gantt Milestone Chart",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Timeline ruler header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Milestone Task",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.width(110.dp)
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (w in 1..totalPlanWeeks step 2) {
                                Text(
                                    text = "W$w",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }

                    // Gantt Bars per milestone
                    milestones.forEach { ms ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ms.taskTitle.take(16) + if (ms.taskTitle.length > 16) ".." else "",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.width(110.dp)
                                )

                                // Horizontal Bar Container representing 1..totalPlanWeeks
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(18.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(4.dp)
                                        )
                                ) {
                                    val startFraction = ((ms.startWeek - 1).toFloat() / totalPlanWeeks.toFloat()).coerceIn(0f, 1f)
                                    val durationFraction = (ms.durationWeeks.toFloat() / totalPlanWeeks.toFloat()).coerceIn(0.05f, 1f)

                                    val barColor = when (ms.status) {
                                        MilestoneStatus.COMPLETED -> Color(0xFF2E7D32)
                                        MilestoneStatus.IN_PROGRESS -> Color(0xFFE65100)
                                        MilestoneStatus.PENDING -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                    }

                                    Row(modifier = Modifier.fillMaxSize()) {
                                        if (startFraction > 0f) {
                                            Spacer(modifier = Modifier.weight(startFraction))
                                        }
                                        Box(
                                            modifier = Modifier
                                                .weight(durationFraction)
                                                .height(18.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(barColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "W${ms.startWeek}-${ms.endWeek}",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        val remaining = 1f - (startFraction + durationFraction)
                                        if (remaining > 0f) {
                                            Spacer(modifier = Modifier.weight(remaining))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Detailed Milestone Cards
        items(milestones, key = { it.id }) { ms ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("milestone_card_${ms.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Weeks ${ms.startWeek} - ${ms.endWeek} (${ms.durationWeeks} wks)",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        // Status Chip with click toggle
                        Surface(
                            color = when (ms.status) {
                                MilestoneStatus.COMPLETED -> Color(0xFFE8F5E9)
                                MilestoneStatus.IN_PROGRESS -> Color(0xFFFFF3E0)
                                MilestoneStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .clickable { onToggleMilestoneStatus(ms.id) }
                                .testTag("btn_toggle_status_${ms.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = when (ms.status) {
                                        MilestoneStatus.COMPLETED -> Icons.Default.CheckCircle
                                        MilestoneStatus.IN_PROGRESS -> Icons.Default.PlayCircle
                                        MilestoneStatus.PENDING -> Icons.Default.HourglassEmpty
                                    },
                                    contentDescription = null,
                                    tint = when (ms.status) {
                                        MilestoneStatus.COMPLETED -> Color(0xFF2E7D32)
                                        MilestoneStatus.IN_PROGRESS -> Color(0xFFE65100)
                                        MilestoneStatus.PENDING -> MaterialTheme.colorScheme.outline
                                    },
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = ms.status.name.replace("_", " "),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (ms.status) {
                                        MilestoneStatus.COMPLETED -> Color(0xFF2E7D32)
                                        MilestoneStatus.IN_PROGRESS -> Color(0xFFE65100)
                                        MilestoneStatus.PENDING -> MaterialTheme.colorScheme.outline
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = ms.taskTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Phase: ${ms.phaseName} • Role: ${ms.responsibleRole}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Deliverable Callout
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "Milestone Deliverable:",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = ms.deliverable,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    if (ms.riskNotes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = Color(0xFFE65100),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Risk Mitigation: ${ms.riskNotes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE65100),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
