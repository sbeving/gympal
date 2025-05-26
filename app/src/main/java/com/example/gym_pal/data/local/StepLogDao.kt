package com.example.gym_pal.data.local

import androidx.room.*
import com.example.gym_pal.data.model.StepLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface StepLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStepLog(stepLog: StepLog)
    
    @Update
    suspend fun updateStepLog(stepLog: StepLog)
    
    @Query("SELECT * FROM step_logs WHERE userId = :userId ORDER BY date DESC")
    fun getAllStepLogsForUser(userId: String): Flow<List<StepLog>>
    
    @Query("SELECT * FROM step_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getStepLogsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<StepLog>>
    
    @Query("SELECT * FROM step_logs WHERE userId = :userId AND date = :date LIMIT 1")
    fun getStepLogForDate(userId: String, date: Date): Flow<StepLog?>
    
    @Query("SELECT SUM(steps) FROM step_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalStepsForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
}
