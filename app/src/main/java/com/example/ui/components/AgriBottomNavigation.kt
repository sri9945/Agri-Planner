package com.example.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.AgriNavTab

@Composable
fun AgriBottomNavigation(
    currentTab: AgriNavTab,
    onTabSelected: (AgriNavTab) -> Unit
) {
    val tabsWithIcons = listOf(
        Triple(AgriNavTab.DASHBOARD, Icons.Default.Dashboard, "Overview"),
        Triple(AgriNavTab.PLAN_BUILDER, Icons.Default.EditNote, "Plan"),
        Triple(AgriNavTab.DATA_SOURCES, Icons.Default.CloudDownload, "Sources"),
        Triple(AgriNavTab.METRICS, Icons.Default.BarChart, "Indicators"),
        Triple(AgriNavTab.PIPELINE, Icons.Default.Schema, "Pipeline"),
        Triple(AgriNavTab.TIMELINE_GANTT, Icons.Default.DateRange, "Timeline"),
        Triple(AgriNavTab.RUBRIC_AUDIT, Icons.Default.Grade, "Rubric"),
        Triple(AgriNavTab.DOC_DELIVERABLE, Icons.Default.Description, "Deliverable")
    )

    ScrollableTabRow(
        selectedTabIndex = currentTab.ordinal,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        edgePadding = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .testTag("agri_nav_bar")
    ) {
        tabsWithIcons.forEach { (tab, icon, label) ->
            val selected = currentTab == tab
            Tab(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                text = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier.testTag(tab.testTag)
            )
        }
    }
}
