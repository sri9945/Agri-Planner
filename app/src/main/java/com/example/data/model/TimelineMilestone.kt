package com.example.data.model

data class TimelineMilestone(
    val id: String,
    val planId: Long = 1L,
    val phaseNumber: Int,
    val phaseName: String,
    val taskTitle: String,
    val startWeek: Int,
    val durationWeeks: Int,
    val deliverable: String,
    val responsibleRole: String,
    val status: MilestoneStatus = MilestoneStatus.PENDING,
    val riskNotes: String = ""
) {
    val endWeek: Int get() = startWeek + durationWeeks - 1
}

enum class MilestoneStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
