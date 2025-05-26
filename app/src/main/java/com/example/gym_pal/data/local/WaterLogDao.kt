package com.example.gym_pal.data.local

import androidx.room.*
import com.example.gym_pal.data.model.WaterLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WaterLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLog: WaterLog): Long
    
    @Update
    suspend fun updateWaterLog(waterLog: WaterLog)
    
    @Delete
    suspend fun deleteWaterLog(waterLog: WaterLog)
    
    @Query("SELECT * FROM water_logs WHERE userId = :userId ORDER BY date DESC")
    fun getAllWaterLogsForUser(userId: String): Flow<List<WaterLog>>
    
    @Query("SELECT * FROM water_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWaterLogsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<WaterLog>>
    
    @Query("SELECT SUM(amount) FROM water_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalWaterForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
}
