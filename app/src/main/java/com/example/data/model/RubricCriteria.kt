package com.example.data.model

data class RubricCriteria(
    val id: String,
    val title: String,
    val weightPercent: Int,
    val maxScore: Int = 20,
    val currentScore: Int = 18,
    val evaluationFocus: String,
    val excellentBenchmark: String,
    val commonWeaknesses: String,
    val actionableGuidance: String
)
