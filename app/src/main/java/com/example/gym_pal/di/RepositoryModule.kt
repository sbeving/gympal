package com.example.gym_pal.di

import android.content.Context
import androidx.room.Room
import com.example.gym_pal.data.local.AppDatabase
import com.example.gym_pal.data.local.CoachConversationDao
import com.example.gym_pal.data.remote.GeminiAiService
import com.example.gym_pal.data.remote.GeminiAiServiceImpl
import com.example.gym_pal.data.repository.CoachRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "gympal-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoachConversationDao(appDatabase: AppDatabase): CoachConversationDao {
        return appDatabase.coachConversationDao()
    }

    @Provides
    @Singleton
    fun provideGeminiAiService(): GeminiAiService {
        return GeminiAiServiceImpl()
    }

    @Provides
    @Singleton
    fun provideCoachRepository(
        coachConversationDao: CoachConversationDao,
        geminiAiService: GeminiAiService
    ): CoachRepository {
        return CoachRepository(coachConversationDao, geminiAiService)
    }
}
