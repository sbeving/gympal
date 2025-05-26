package com.example.gym_pal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gym_pal.data.model.*

@Database(
    entities = [
        User::class,
        NutritionLog::class,
        WaterLog::class,
        WorkoutLog::class,
        StepLog::class,
        CoachConversation::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GymPalDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun nutritionLogDao(): NutritionLogDao
    abstract fun waterLogDao(): WaterLogDao
    abstract fun workoutLogDao(): WorkoutLogDao
    abstract fun stepLogDao(): StepLogDao
    abstract fun coachConversationDao(): CoachConversationDao
    
    companion object {
        @Volatile
        private var INSTANCE: GymPalDatabase? = null
        
        fun getDatabase(context: Context): GymPalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymPalDatabase::class.java,
                    "gympal_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
