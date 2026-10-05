package com.example.data.model

data class AgriMetric(
    val id: String,
    val category: String, // "Crop Yields & Acreage", "Pricing Dynamics & Futures", "Resource Utilization & Inputs", "Trade & Macro Dynamics", "Agrometeorology & Health"
    val name: String,
    val code: String,
    val unit: String,
    val formulaOrMethod: String,
    val description: String,
    val agribusinessSignificance: String,
    val recommendedSources: List<String>,
    val defaultBenchmark: String
)
