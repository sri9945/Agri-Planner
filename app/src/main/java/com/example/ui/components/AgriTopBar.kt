package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgriPlanEntity
import com.example.ui.viewmodel.AgriNavTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgriTopBar(
    activePlan: AgriPlanEntity?,
    allPlans: List<AgriPlanEntity>,
    onSelectPlan: (AgriPlanEntity) -> Unit,
    onExportClick: () -> Unit,
    onTabSelected: (AgriNavTab) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(
                onClick = { onTabSelected(AgriNavTab.DASHBOARD) },
                modifier = Modifier.testTag("btn_home_icon")
            ) {
                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = "AgriData Planner Home",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        title = {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp)
                ) {
                    Text(
                        text = "AgriData Planner",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = activePlan?.title ?: "Select Plan",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier.size(24.dp).testTag("btn_switch_plan")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Switch Agribusiness Plan",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    Text(
                        text = "Switch Research Plan",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                    allPlans.forEach { plan ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = plan.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${plan.sector} • ${plan.status}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            },
                            onClick = {
                                onSelectPlan(plan)
                                menuExpanded = false
                            },
                            modifier = Modifier.testTag("plan_item_${plan.id}")
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(
                onClick = { onTabSelected(AgriNavTab.DOC_DELIVERABLE) },
                modifier = Modifier.testTag("btn_quick_doc")
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "View Plan Document"
                )
            }
            IconButton(
                onClick = onExportClick,
                modifier = Modifier.testTag("btn_share_plan")
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Plan DOC"
                )
            }
        }
    )
}
