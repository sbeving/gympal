package com.example.gym_pal.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service interface for interacting with Gemini AI.
 * Used to get fitness advice from AI.
 */
interface GeminiAiService {
    /**
     * Gets fitness advice based on the provided prompt.
     *
     * @param prompt The user's prompt/question about fitness
     * @return The AI-generated fitness advice as a string
     */
    suspend fun getFitnessAdvice(prompt: String): String
}

@Singleton
class GeminiAiServiceImpl @Inject constructor() : GeminiAiService {
    
    // Replace with your actual API key from Google AI Studio
    private val apiKey = "AIzaSyD7l5Zn3eR2rD_JhCDE0Wr_aSrSBfTV6rk"
    
    private val geminiModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = apiKey
    )
    
    override suspend fun getFitnessAdvice(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val response: GenerateContentResponse = geminiModel.generateContent(buildPrompt(prompt))
            return@withContext response.text ?: "Sorry, I couldn't generate advice at this time."
        } catch (e: Exception) {
            return@withContext "Error: ${e.localizedMessage}"
        }
    }
    
    private fun buildPrompt(userQuery: String): String {
        return """
            You are a professional fitness coach named GymPal Coach. Your expertise is in fitness, 
            nutrition, and overall wellness. Please provide helpful, motivating, and scientifically 
            accurate advice for the following query from a user.
            
            Use Markdown formatting to make your response more readable:
            - Use **bold** for important points or keywords
            - Use *italic* for emphasis
            - Use bullet points for lists
            - Use headers (## or ###) for section titles
            - Format any workout routines or nutrition plans in a structured way
            - If providing exercise sets, format them in tables
            
            Keep responses concise (under 300 words), well-formatted, and easy to understand.
            
            User query: $userQuery
        """.trimIndent()
    }
}
