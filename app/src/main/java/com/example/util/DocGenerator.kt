package com.example.util

import com.example.data.model.AgriMetric
import com.example.data.model.AgriPlanEntity
import com.example.data.model.DataSource
import com.example.data.model.PipelineStage
import com.example.data.model.RubricCriteria
import com.example.data.model.TimelineMilestone

object DocGenerator {

    fun generateFullPlanDocument(
        plan: AgriPlanEntity,
        sources: List<DataSource>,
        metrics: List<AgriMetric>,
        stages: List<PipelineStage>,
        milestones: List<TimelineMilestone>,
        rubricCriteria: List<RubricCriteria>
    ): String {
        val selectedSources = sources.filter { plan.selectedSourceIds.contains(it.id) }
        val selectedMetrics = metrics.filter { plan.selectedMetricIds.contains(it.id) }

        val sb = StringBuilder()

        // Document Header & Metadata
        sb.append("================================================================================\n")
        sb.append("AGRIBUSINESS DATA ANALYSIS & MARKET TREND FORECASTING PLAN\n")
        sb.append("Comprehensive Research Strategy, Indicator Architecture, Pipeline & Timeline\n")
        sb.append("================================================================================\n\n")

        sb.append("PROJECT TITLE:     ${plan.title}\n")
        sb.append("COMMODITY FOCUS:   ${plan.targetCommodities}\n")
        sb.append("INDUSTRY SECTOR:   ${plan.sector}\n")
        sb.append("AUTHOR / RESEARCH: ${plan.studentAuthor}\n")
        sb.append("INSTITUTION:       ${plan.institution}\n")
        sb.append("SUBMISSION DATE:   ${plan.submissionDate}\n")
        sb.append("DOCUMENT STATUS:   ${plan.status} | Total Duration: ${plan.totalWeeks} Weeks\n\n")
        sb.append("--------------------------------------------------------------------------------\n\n")

        // 1.0 Executive Summary
        sb.append("1.0 EXECUTIVE SUMMARY\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("${plan.executiveSummary}\n\n")
        sb.append("Key Highlights of Analytical Strategy:\n")
        sb.append("  • Rigorous ingestion of authoritative public agribusiness datasets.\n")
        sb.append("  • Triangulation of agronomic crop yield trends, pricing dynamics, and resource costs.\n")
        sb.append("  • End-to-end 6-stage data engineering, econometric, and machine learning pipeline.\n")
        sb.append("  • ${plan.totalWeeks}-week milestone schedule with dedicated risk buffers and cross-validation.\n\n")

        // 2.0 Research Rationale & Problem Statement
        sb.append("2.0 RESEARCH RATIONALE & AGRIBUSINESS CONTEXT\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("2.1 Agribusiness Rationale:\n")
        sb.append("${plan.researchRationale}\n\n")

        sb.append("2.2 Core Problem Statement:\n")
        sb.append("${plan.problemStatement}\n\n")

        sb.append("2.3 Key Research & Analytical Questions:\n")
        val questions = plan.researchQuestions.split("\n").filter { it.isNotBlank() }
        questions.forEach { q ->
            sb.append("  • $q\n")
        }
        sb.append("\n")

        // 3.0 Key Indicators & Metrics Taxonomy
        sb.append("3.0 KEY INDICATORS & METRICS TAXONOMY\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("The analysis synthesizes indicators across three core agribusiness pillars:\n")
        sb.append("(A) Crop Yields & Acreage Productivity, (B) Pricing Dynamics & Market Spreads,\n")
        sb.append("and (C) Resource Utilization & Input Intensity.\n\n")

        val groupedMetrics = selectedMetrics.groupBy { it.category }
        groupedMetrics.forEach { (category, metricList) ->
            sb.append("3.${getCategorySubindex(category)} Category: $category\n")
            metricList.forEach { m ->
                sb.append("  • [${m.code}] ${m.name}\n")
                sb.append("    - Measurement Unit:      ${m.unit}\n")
                sb.append("    - Formula / Algorithm:   ${m.formulaOrMethod}\n")
                sb.append("    - Agribusiness Context:  ${m.agribusinessSignificance}\n")
                sb.append("    - Benchmark Standard:    ${m.defaultBenchmark}\n\n")
            }
        }

        // 4.0 Public Agribusiness Data Sources
        sb.append("4.0 PROPOSED PUBLIC DATA SOURCES & INGESTION ARCHITECTURE\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("This study exclusively relies on authoritative, publicly accessible, open-license\n")
        sb.append("repositories to ensure full analytical reproducibility and auditability.\n\n")

        selectedSources.forEachIndexed { idx, src ->
            sb.append("4.${idx + 1} ${src.name} (${src.agency})\n")
            sb.append("    - Public Domain Portal: ${src.publicUrl}\n")
            sb.append("    - Data Protocol & API:  ${src.accessProtocol} | Endpoint: ${src.apiEndpoint}\n")
            sb.append("    - Update Frequency:     ${src.updateFrequency}\n")
            sb.append("    - Geographic Granularity: ${src.geographicCoverage}\n")
            sb.append("    - Core Extracted Data:  ${src.keyVariables.joinToString(", ")}\n")
            sb.append("    - Reliability Index:    ${src.reliabilityRating} / 5.0 | Licensing: ${src.licensing}\n")
            sb.append("    - Automated Query Sample:\n")
            sb.append("      ${src.querySnippet.replace("\n", "\n      ")}\n\n")
        }

        // 5.0 Step-by-Step Analytical Pipeline
        sb.append("5.0 STEP-BY-STEP DATA COLLECTION, CLEANING & INTERPRETATION PIPELINE\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("A phased, reproducible analytical pipeline is implemented to transform heterogeneous\n")
        sb.append("raw public feeds into predictive market trend models and risk distributions.\n\n")

        stages.forEach { stage ->
            sb.append("5.${stage.stageNumber} Stage ${stage.stageNumber}: ${stage.title}\n")
            sb.append("    • Phase Objective: ${stage.objective}\n")
            sb.append("    • Technical Methods:\n")
            stage.technicalMethods.forEach { tm ->
                sb.append("      - $tm\n")
            }
            sb.append("    • Software & Frameworks: ${stage.toolsAndFrameworks.joinToString(", ")}\n")
            sb.append("    • Quality Assurance & Validation Tasks:\n")
            stage.qualityAssuranceTasks.forEach { qa ->
                sb.append("      [√] $qa\n")
            }
            sb.append("    • Input Artifacts:       ${stage.inputArtifacts}\n")
            sb.append("    • Output Deliverables:   ${stage.outputDeliverables}\n")
            sb.append("    • Pitfall Mitigation:    ${stage.keyPitfallsAndMitigations}\n\n")
        }

        // 6.0 Timeline & Milestone Roadmap (Gantt Schedule)
        sb.append("6.0 PROJECT TIMELINE & MILESTONE ROADMAP\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("Total Project Execution Window: ${plan.totalWeeks} Weeks\n\n")
        sb.append(String.format("%-10s | %-38s | %-12s | %-12s\n", "SCHEDULE", "MILESTONE TASK", "STATUS", "ROLE"))
        sb.append("--------------------------------------------------------------------------------\n")
        milestones.forEach { ms ->
            val weekSpan = "Wk ${ms.startWeek}-${ms.endWeek}"
            val titleTrimmed = if (ms.taskTitle.length > 36) ms.taskTitle.substring(0, 33) + "..." else ms.taskTitle
            val roleTrimmed = if (ms.responsibleRole.length > 12) ms.responsibleRole.substring(0, 10) + ".." else ms.responsibleRole
            sb.append(String.format("%-10s | %-38s | %-12s | %-12s\n", weekSpan, titleTrimmed, ms.status.name, roleTrimmed))
            sb.append("   Deliverable: ${ms.deliverable}\n")
            if (ms.riskNotes.isNotBlank()) {
                sb.append("   Risk Buffer: ${ms.riskNotes}\n")
            }
            sb.append("\n")
        }

        // 7.0 Evaluation & Self-Audit Rubric
        sb.append("7.0 METHODOLOGICAL EVALUATION & RUBRIC AUDIT\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("Self-evaluation benchmarked against academic and industry agribusiness evaluation standards:\n\n")

        var totalCurrent = 0
        var totalPossible = 0
        rubricCriteria.forEach { r ->
            totalCurrent += r.currentScore
            totalPossible += r.maxScore
            sb.append("  • ${r.title} (Weight: ${r.weightPercent}%) : ${r.currentScore} / ${r.maxScore} pts\n")
            sb.append("    - Focus: ${r.evaluationFocus}\n")
            sb.append("    - Benchmark Standard: ${r.excellentBenchmark}\n")
            sb.append("    - Actionable Guidance: ${r.actionableGuidance}\n\n")
        }
        val pct = if (totalPossible > 0) (totalCurrent * 100) / totalPossible else 0
        sb.append("TOTAL EVALUATION SCORE: $totalCurrent / $totalPossible Points ($pct%) - Grade: ${getGrade(pct)}\n\n")

        // 8.0 Concluding Remarks & References
        sb.append("8.0 CONCLUDING REMARKS & CITATIONS\n")
        sb.append("--------------------------------------------------------------------------------\n")
        sb.append("This data analysis plan establishes an objective, evidence-based methodology\n")
        sb.append("to interpret volatile agricultural commodity cycles. By linking biological crop\n")
        sb.append("condition telemetry with global macroeconomic balance sheets, agribusiness decision-makers\n")
        sb.append("gain actionable forecasting leverage.\n\n")
        sb.append("Selected Primary Citations:\n")
        sb.append("  1. USDA National Agricultural Statistics Service (NASS). QuickStats Database API.\n")
        sb.append("  2. Food and Agriculture Organization of the United Nations (FAO). FAOSTAT Analytical Accounts.\n")
        sb.append("  3. World Bank Development Prospects Group. Commodity Markets Outlook (Pink Sheet).\n")
        sb.append("  4. European Centre for Medium-Range Weather Forecasts (ECMWF). AgERA5 Agrometeorological Reanalysis.\n")
        sb.append("  5. Chicago Mercantile Exchange (CME Group). Agricultural Commodity Futures & Options Specifications.\n")
        sb.append("================================================================================\n")
        sb.append("END OF COMPREHENSIVE AGRIBUSINESS DATA ANALYSIS DELIVERABLE\n")

        return sb.toString()
    }

    private fun getCategorySubindex(category: String): String {
        return when {
            category.contains("Yield", ignoreCase = true) -> "1"
            category.contains("Pric", ignoreCase = true) -> "2"
            category.contains("Resource", ignoreCase = true) || category.contains("Input", ignoreCase = true) -> "3"
            category.contains("Trade", ignoreCase = true) -> "4"
            else -> "5"
        }
    }

    private fun getGrade(percentage: Int): String {
        return when {
            percentage >= 93 -> "A (Distinction - Exceeds Criteria)"
            percentage >= 85 -> "A- (Excellent - Meets All Criteria)"
            percentage >= 78 -> "B+ (Commendable - Minor Polish Needed)"
            percentage >= 70 -> "B (Satisfactory)"
            else -> "Revisions Recommended"
        }
    }
}
