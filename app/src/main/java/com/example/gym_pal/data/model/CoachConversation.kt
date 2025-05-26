package com.example.gym_pal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "coach_conversations")
data class CoachConversation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val message: String,
    val isUserMessage: Boolean,
    val timestamp: Date
)
