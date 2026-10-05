package com.example.data.repository

import com.example.data.local.AgriPlanDao
import com.example.data.model.AgriMetric
import com.example.data.model.AgriPlanEntity
import com.example.data.model.DataSource
import com.example.data.model.PipelineStage
import com.example.data.model.RubricCriteria
import com.example.data.model.TimelineMilestone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class AgriPlanRepository(private val dao: AgriPlanDao) {

    val allPlans: Flow<List<AgriPlanEntity>> = dao.getAllPlans()

    suspend fun initializePrepopulatedDataIfNeeded() = withContext(Dispatchers.IO) {
        val count = dao.getPlanCount()
        if (count == 0) {
            dao.insertPlans(PrepopulatedAgriData.preloadedPlans)
        }
    }

    fun getPlanById(id: Long): Flow<AgriPlanEntity?> {
        return dao.getPlanById(id)
    }

    suspend fun getPlanByIdOnce(id: Long): AgriPlanEntity? = withContext(Dispatchers.IO) {
        dao.getPlanByIdOnce(id)
    }

    suspend fun savePlan(plan: AgriPlanEntity): Long = withContext(Dispatchers.IO) {
        if (plan.id == 0L) {
            dao.insertPlan(plan.copy(updatedAt = System.currentTimeMillis()))
        } else {
            dao.updatePlan(plan.copy(updatedAt = System.currentTimeMillis()))
            plan.id
        }
    }

    suspend fun deletePlan(id: Long) = withContext(Dispatchers.IO) {
        dao.deletePlanById(id)
    }

    fun getAllDataSources(): List<DataSource> {
        return PrepopulatedAgriData.publicDataSources
    }

    fun getAllMetrics(): List<AgriMetric> {
        return PrepopulatedAgriData.keyMetrics
    }

    fun getAllPipelineStages(): List<PipelineStage> {
        return PrepopulatedAgriData.pipelineStages
    }

    fun getDefaultMilestones(): List<TimelineMilestone> {
        return PrepopulatedAgriData.defaultMilestones
    }

    fun getRubricCriteriaList(): List<RubricCriteria> {
        return PrepopulatedAgriData.rubricCriteriaList
    }
}
