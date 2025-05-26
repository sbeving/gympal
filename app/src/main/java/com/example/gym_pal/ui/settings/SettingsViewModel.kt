package com.example.gym_pal.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_pal.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {
    
    // Expose preference flows from the repository
    val themeModeFlow = preferencesRepository.themeModeFlow
    val notificationEnabledFlow = preferencesRepository.notificationEnabledFlow
    val waterReminderIntervalFlow = preferencesRepository.waterReminderIntervalFlow
    val workoutReminderTimeFlow = preferencesRepository.workoutReminderTimeFlow
    val unitsMetricFlow = preferencesRepository.unitsMetricFlow
    
    // Save theme mode setting
    fun saveThemeMode(themeMode: String) {
        viewModelScope.launch {
            preferencesRepository.saveThemeMode(themeMode)
        }
    }
    
    // Save notifications enabled setting
    fun saveNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.saveNotificationEnabled(enabled)
        }
    }
    
    // Save water reminder interval
    fun saveWaterReminderInterval(minutes: Int) {
        viewModelScope.launch {
            preferencesRepository.saveWaterReminderInterval(minutes)
        }
    }
    
    // Save workout reminder time
    fun saveWorkoutReminderTime(time: String) {
        viewModelScope.launch {
            preferencesRepository.saveWorkoutReminderTime(time)
        }
    }
    
    // Save units preference
    fun saveUnitsMetric(isMetric: Boolean) {
        viewModelScope.launch {
            preferencesRepository.saveUnitsMetric(isMetric)
        }
    }
}
