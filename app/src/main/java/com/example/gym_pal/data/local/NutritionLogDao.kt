package com.example.gym_pal.data.local

import androidx.room.*
import com.example.gym_pal.data.model.NutritionLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NutritionLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutritionLog(nutritionLog: NutritionLog): Long
    
    @Update
    suspend fun updateNutritionLog(nutritionLog: NutritionLog)
    
    @Delete
    suspend fun deleteNutritionLog(nutritionLog: NutritionLog)
    
    @Query("SELECT * FROM nutrition_logs WHERE userId = :userId ORDER BY date DESC")
    fun getAllNutritionLogsForUser(userId: String): Flow<List<NutritionLog>>
    
    @Query("SELECT * FROM nutrition_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getNutritionLogsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<NutritionLog>>
    
    @Query("SELECT SUM(calories) FROM nutrition_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalCaloriesForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
    
    @Query("SELECT SUM(protein) FROM nutrition_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalProteinForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Float>
}
