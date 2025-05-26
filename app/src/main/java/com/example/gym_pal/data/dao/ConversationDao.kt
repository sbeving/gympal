package com.example.gym_pal.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gym_pal.data.model.Conversation

@Dao
interface ConversationDao {
    // Existing methods...
    
    @Query("SELECT * FROM conversations WHERE userId = :userId ORDER BY timestamp DESC")
    fun getConversationsForUser(userId: String): LiveData<List<Conversation>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation): Long
    
    @Query("DELETE FROM conversations WHERE userId = :userId")
    suspend fun clearConversationsForUser(userId: String)
}
