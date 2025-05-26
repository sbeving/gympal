package com.example.gym_pal.data.repository

import com.example.gym_pal.data.local.NutritionLogDao
import com.example.gym_pal.data.local.StepLogDao
import com.example.gym_pal.data.local.WaterLogDao
import com.example.gym_pal.data.local.WorkoutLogDao
import com.example.gym_pal.data.model.NutritionLog
import com.example.gym_pal.data.model.StepLog
import com.example.gym_pal.data.model.WaterLog
import com.example.gym_pal.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class TrackingRepository @Inject constructor(
    private val nutritionLogDao: NutritionLogDao,
    private val waterLogDao: WaterLogDao,
    private val workoutLogDao: WorkoutLogDao,
    private val stepLogDao: StepLogDao
) {
    // Helper function to get start/end of day
    private fun getStartAndEndOfDay(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startDate = calendar.time
        
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val endDate = calendar.time
        
        return Pair(startDate, endDate)
    }
    
    // Water Tracking
    suspend fun addWaterIntake(userId: String, amount: Int) {
        val waterLog = WaterLog(
            userId = userId,
            amount = amount,
            date = Date()
        )
        waterLogDao.insertWaterLog(waterLog)
    }
    
    fun getTodayTotalWaterIntake(userId: String): Flow<Int> {
        val (startDate, endDate) = getStartAndEndOfDay(Date())
        return waterLogDao.getTotalWaterForDateRange(userId, startDate, endDate)
    }
    
    // Food/Nutrition Tracking
    suspend fun addNutritionLog(
        userId: String,
        foodName: String,
        calories: Int,
        protein: Float,
        carbs: Float,
        fat: Float,
        mealType: String
    ) {
        val nutritionLog = NutritionLog(
            userId = userId,
            foodName = foodName,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            date = Date(),
            mealType = mealType
        )
        nutritionLogDao.insertNutritionLog(nutritionLog)
    }
    
    fun getTodayTotalCalories(userId: String): Flow<Int> {
        val (startDate, endDate) = getStartAndEndOfDay(Date())
        return nutritionLogDao.getTotalCaloriesForDateRange(userId, startDate, endDate)
    }
    
    fun getTodayTotalProtein(userId: String): Flow<Float> {
        val (startDate, endDate) = getStartAndEndOfDay(Date())
        return nutritionLogDao.getTotalProteinForDateRange(userId, startDate, endDate)
    }
    
    // Step Tracking
    suspend fun updateStepCount(userId: String, steps: Int) {
        val today = Date()
        val (startDate, _) = getStartAndEndOfDay(today)
        val stepLog = StepLog(
            date = startDate,
            userId = userId,
            steps = steps
        )
        stepLogDao.insertStepLog(stepLog)
    }
    
    fun getTodayStepCount(userId: String): Flow<StepLog?> {
        val (today, _) = getStartAndEndOfDay(Date())
        return stepLogDao.getStepLogForDate(userId, today)
    }
    
    // Workout Tracking
    suspend fun addWorkoutLog(
        userId: String,
        workoutName: String,
        duration: Int,
        caloriesBurned: Int
    ) {
        val workoutLog = WorkoutLog(
            userId = userId,
            workoutName = workoutName,
            duration = duration,
            caloriesBurned = caloriesBurned,
            date = Date()
        )
        workoutLogDao.insertWorkoutLog(workoutLog)
    }
    
    fun getTodayWorkoutCount(userId: String): Flow<Int> {
        val (startDate, endDate) = getStartAndEndOfDay(Date())
        return workoutLogDao.getWorkoutCountForDateRange(userId, startDate, endDate)
    }
}
