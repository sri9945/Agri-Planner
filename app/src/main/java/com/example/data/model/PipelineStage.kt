package com.example.data.model

data class PipelineStage(
    val stageNumber: Int,
    val phaseName: String,
    val title: String,
    val objective: String,
    val technicalMethods: List<String>,
    val toolsAndFrameworks: List<String>,
    val qualityAssuranceTasks: List<String>,
    val inputArtifacts: String,
    val outputDeliverables: String,
    val keyPitfallsAndMitigations: String
)
