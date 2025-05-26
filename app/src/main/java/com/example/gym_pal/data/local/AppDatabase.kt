package com.example.gym_pal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gym_pal.data.model.CoachConversation
import com.example.gym_pal.data.model.Conversation

@Database(
    entities = [CoachConversation::class, Conversation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coachConversationDao(): CoachConversationDao
}
