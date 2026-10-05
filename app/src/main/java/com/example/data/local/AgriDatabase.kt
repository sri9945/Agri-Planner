package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.AgriPlanEntity

@Database(
    entities = [AgriPlanEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AgriDatabase : RoomDatabase() {
    abstract fun agriPlanDao(): AgriPlanDao

    companion object {
        @Volatile
        private var INSTANCE: AgriDatabase? = null

        fun getDatabase(context: Context): AgriDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgriDatabase::class.java,
                    "agribiz_analysis_plans.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
