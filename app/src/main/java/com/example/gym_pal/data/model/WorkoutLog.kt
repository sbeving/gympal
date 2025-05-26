package com.example.gym_pal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val workoutName: String,
    val duration: Int, // in minutes
    val caloriesBurned: Int,
    val date: Date
)
