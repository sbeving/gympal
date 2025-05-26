package com.example.gym_pal.data.repository

import com.example.gym_pal.data.local.CoachConversationDao
import com.example.gym_pal.data.model.CoachConversation
import com.example.gym_pal.data.remote.GeminiAiService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoachRepository @Inject constructor(
    private val coachConversationDao: CoachConversationDao,
    private val geminiAiService: GeminiAiService
) {
    
    fun getConversationsForUser(userId: String): Flow<List<CoachConversation>> {
        return coachConversationDao.getAllCoachConversationsForUser(userId)
    }
    
    suspend fun sendMessage(userId: String, message: String): CoachConversation {
        // Save user message to database
        val userMessage = CoachConversation(
            userId = userId,
            message = message,
            isUserMessage = true,
            timestamp = Date()
        )
        coachConversationDao.insertCoachConversation(userMessage)
        
        // Get response from AI
        val aiResponse = geminiAiService.getFitnessAdvice(message)
        
        // Save AI response to database
        val aiMessage = CoachConversation(
            userId = userId,
            message = aiResponse,
            isUserMessage = false,
            timestamp = Date()
        )
        coachConversationDao.insertCoachConversation(aiMessage)
        
        return aiMessage
    }
    
    suspend fun clearConversationHistory(userId: String) {
        coachConversationDao.deleteAllCoachConversationsForUser(userId)
    }
}
