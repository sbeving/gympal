package com.example.gym_pal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "step_logs")
data class StepLog(
    @PrimaryKey
    val date: Date,
    val userId: String,
    val steps: Int
)
