package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AgriPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgriPlanDao {
    @Query("SELECT * FROM agri_plans ORDER BY updatedAt DESC")
    fun getAllPlans(): Flow<List<AgriPlanEntity>>

    @Query("SELECT * FROM agri_plans WHERE id = :id LIMIT 1")
    fun getPlanById(id: Long): Flow<AgriPlanEntity?>

    @Query("SELECT * FROM agri_plans WHERE id = :id LIMIT 1")
    suspend fun getPlanByIdOnce(id: Long): AgriPlanEntity?

    @Query("SELECT COUNT(*) FROM agri_plans")
    suspend fun getPlanCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: AgriPlanEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlans(plans: List<AgriPlanEntity>)

    @Update
    suspend fun updatePlan(plan: AgriPlanEntity)

    @Query("DELETE FROM agri_plans WHERE id = :id")
    suspend fun deletePlanById(id: Long)
}
