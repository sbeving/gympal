package com.example.gym_pal.data.local

import androidx.room.*
import com.example.gym_pal.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkoutLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(workoutLog: WorkoutLog): Long
    
    @Update
    suspend fun updateWorkoutLog(workoutLog: WorkoutLog)
    
    @Delete
    suspend fun deleteWorkoutLog(workoutLog: WorkoutLog)
    
    @Query("SELECT * FROM workout_logs WHERE userId = :userId ORDER BY date DESC")
    fun getAllWorkoutLogsForUser(userId: String): Flow<List<WorkoutLog>>
    
    @Query("SELECT * FROM workout_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkoutLogsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<WorkoutLog>>
    
    @Query("SELECT SUM(duration) FROM workout_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalWorkoutDurationForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
    
    @Query("SELECT SUM(caloriesBurned) FROM workout_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getTotalCaloriesBurnedForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM workout_logs WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    fun getWorkoutCountForDateRange(userId: String, startDate: Date, endDate: Date): Flow<Int>
}
