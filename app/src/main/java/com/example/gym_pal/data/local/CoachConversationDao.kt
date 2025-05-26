package com.example.gym_pal.data.local

import androidx.room.*
import com.example.gym_pal.data.model.CoachConversation
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachConversationDao {
    @Query("SELECT * FROM coach_conversations WHERE userId = :userId ORDER BY timestamp ASC")
    fun getAllCoachConversationsForUser(userId: String): Flow<List<CoachConversation>>
    
    @Insert
    suspend fun insertCoachConversation(conversation: CoachConversation): Long
    
    @Query("DELETE FROM coach_conversations WHERE userId = :userId")
    suspend fun deleteAllCoachConversationsForUser(userId: String)
}
