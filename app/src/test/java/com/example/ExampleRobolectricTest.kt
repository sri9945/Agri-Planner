package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.PrepopulatedAgriData
import com.example.util.DocGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AgriData Planner", appName)
  }

  @Test
  fun `verify document generator produces structured deliverable`() {
    val plan = PrepopulatedAgriData.preloadedPlans.first()
    val docText = DocGenerator.generateFullPlanDocument(
        plan = plan,
        sources = PrepopulatedAgriData.publicDataSources,
        metrics = PrepopulatedAgriData.keyMetrics,
        stages = PrepopulatedAgriData.pipelineStages,
        milestones = PrepopulatedAgriData.defaultMilestones,
        rubricCriteria = PrepopulatedAgriData.rubricCriteriaList
    )

    assertNotNull(docText)
    assertTrue(docText.contains("AGRIBUSINESS DATA ANALYSIS & MARKET TREND FORECASTING PLAN"))
    assertTrue(docText.contains("1.0 EXECUTIVE SUMMARY"))
    assertTrue(docText.contains("3.0 KEY INDICATORS & METRICS TAXONOMY"))
    assertTrue(docText.contains("4.0 PROPOSED PUBLIC DATA SOURCES & INGESTION ARCHITECTURE"))
    assertTrue(docText.contains("5.0 STEP-BY-STEP DATA COLLECTION, CLEANING & INTERPRETATION PIPELINE"))
    assertTrue(docText.contains("6.0 PROJECT TIMELINE & MILESTONE ROADMAP"))
    assertTrue(docText.contains("7.0 METHODOLOGICAL EVALUATION & RUBRIC AUDIT"))
  }
}
