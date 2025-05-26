package com.example.gym_pal.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Create the DataStore instance at the top level
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Keys for preferences
    private object PreferencesKeys {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val THEME_MODE = stringPreferencesKey("theme_mode") // "light", "dark", "system"
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val WATER_REMINDER_INTERVAL = intPreferencesKey("water_reminder_interval") // minutes
        val WORKOUT_REMINDER_TIME = stringPreferencesKey("workout_reminder_time") // HH:MM
        val UNITS_METRIC = booleanPreferencesKey("units_metric") // true for metric, false for imperial
    }

    // User ID
    val userIdFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_ID] ?: "" }
    
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }
    
    // User Name
    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_NAME] ?: "" }
    
    suspend fun saveUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }
    
    // Theme Mode
    val themeModeFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.THEME_MODE] ?: "system" }
    
    suspend fun saveThemeMode(themeMode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = themeMode
        }
    }
    
    // Notification Preferences
    val notificationEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.NOTIFICATION_ENABLED] ?: true }
    
    suspend fun saveNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = enabled
        }
    }
    
    // Water Reminder Interval (in minutes)
    val waterReminderIntervalFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.WATER_REMINDER_INTERVAL] ?: 60 } // Default: 1 hour
    
    suspend fun saveWaterReminderInterval(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.WATER_REMINDER_INTERVAL] = minutes
        }
    }
    
    // Workout Reminder Time (HH:MM)
    val workoutReminderTimeFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.WORKOUT_REMINDER_TIME] ?: "18:00" } // Default: 6 PM
    
    suspend fun saveWorkoutReminderTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.WORKOUT_REMINDER_TIME] = time
        }
    }
    
    // Units (Metric vs Imperial)
    val unitsMetricFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.UNITS_METRIC] ?: true } // Default: Metric
    
    suspend fun saveUnitsMetric(isMetric: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.UNITS_METRIC] = isMetric
        }
    }
    
    // Clear all user data (for logout)
    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_ID)
            preferences.remove(PreferencesKeys.USER_NAME)
            // Keep other preferences like theme, units, etc.
        }
    }
}
