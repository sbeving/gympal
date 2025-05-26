package com.example.gym_pal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val name: String,
    val email: String,
    val height: Float = 0f,
    val weight: Float = 0f,
    val age: Int = 0,
    val gender: String = "",
    val dailyStepGoal: Int = 10000,
    val dailyWaterGoal: Int = 8,  // in glasses
    val dailyCalorieGoal: Int = 2000
)
