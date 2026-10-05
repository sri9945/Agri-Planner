package com.example.data.model

data class DataSource(
    val id: String,
    val name: String,
    val agency: String,
    val domain: String, // "Production & Yields", "Pricing & Commodities", "Input & Resources", "Macro & Trade", "Climate & Earth Observation"
    val description: String,
    val publicUrl: String,
    val apiEndpoint: String,
    val apiDocUrl: String,
    val accessProtocol: String, // e.g. "REST API / JSON", "Bulk CSV / S3", "GeoTIFF / OGC WMS"
    val updateFrequency: String, // "Daily", "Weekly", "Monthly", "Annual"
    val geographicCoverage: String, // "Global", "US County/State", "EU Member States", "Sub-Saharan Africa"
    val keyVariables: List<String>,
    val reliabilityRating: Float, // 1.0 - 5.0
    val licensing: String, // "Public Domain (CC0 / Open Data)"
    val querySnippet: String // Example curl or Python request
)
