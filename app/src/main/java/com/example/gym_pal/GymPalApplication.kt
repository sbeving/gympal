package com.example.gym_pal

import android.app.Application
import com.example.gym_pal.data.repository.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class GymPalApplication : Application() {
    
    // Create an application-level coroutine scope
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository
    
    override fun onCreate() {
        super.onCreate()
        initializeUserId()
    }
    
    private fun initializeUserId() {
        // Initialize a default user ID if none exists
        applicationScope.launch(Dispatchers.IO) {
            val existingUserId = userPreferencesRepository.userIdFlow.first()
            if (existingUserId.isEmpty()) {
                // Generate a new user ID if one doesn't exist
                val newUserId = UUID.randomUUID().toString()
                userPreferencesRepository.saveUserId(newUserId)
                
                // You can also set a default name if needed
                userPreferencesRepository.saveUserName("GymPal User")
            }
        }
    }
}
