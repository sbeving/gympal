package com.example.gym_pal.ui.coach

import java.util.Date

data class Message(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Date = Date()
)
