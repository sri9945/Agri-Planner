package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agri_plans")
data class AgriPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val sector: String,
    val targetCommodities: String,
    val studentAuthor: String,
    val institution: String,
    val submissionDate: String,
    val executiveSummary: String,
    val researchRationale: String,
    val problemStatement: String,
    val researchQuestions: String, // newline or pipe delimited
    val selectedSourceIds: String, // comma separated IDs
    val selectedMetricIds: String, // comma separated IDs
    val totalWeeks: Int = 14,
    val status: String = "In Review",
    val customNotes: String = "",
    val isPreloaded: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
