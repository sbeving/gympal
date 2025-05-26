package com.example.gym_pal.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentSettingsBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupThemeSelector()
        setupUnitToggle()
        setupNotificationToggle()
        setupWaterReminderInterval()
        setupWorkoutReminderTime()
        
        loadUserPreferences()
    }
    
    private fun setupThemeSelector() {
        val themes = arrayOf("Light", "Dark", "System Default")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, themes)
        (binding.tilTheme.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        
        binding.etTheme.setOnItemClickListener { _, _, position, _ ->
            val selectedTheme = when (position) {
                0 -> "light"
                1 -> "dark"
                else -> "system"
            }
            viewModel.saveThemeMode(selectedTheme)
            // Apply theme change immediately
            updateTheme(selectedTheme)
        }
    }
    
    private fun updateTheme(themeMode: String) {
        // Implementation will depend on how you want to handle theme changes
        // This might involve recreating the activity or using AppCompatDelegate
    }
    
    private fun setupUnitToggle() {
        binding.switchMetricUnits.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveUnitsMetric(isChecked)
        }
    }
    
    private fun setupNotificationToggle() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveNotificationEnabled(isChecked)
            binding.layoutReminderSettings.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }
    
    private fun setupWaterReminderInterval() {
        val intervals = arrayOf("30 minutes", "1 hour", "2 hours", "3 hours", "4 hours")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, intervals)
        (binding.tilWaterReminder.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        
        binding.etWaterReminder.setOnItemClickListener { _, _, position, _ ->
            val minutes = when (position) {
                0 -> 30
                1 -> 60
                2 -> 120
                3 -> 180
                4 -> 240
                else -> 60
            }
            viewModel.saveWaterReminderInterval(minutes)
        }
    }
    
    private fun setupWorkoutReminderTime() {
        binding.etWorkoutReminder.setOnClickListener {
            // Extract current hour and minute from saved preference
            val savedTime = binding.etWorkoutReminder.text.toString()
            val hour = if (savedTime.isNotEmpty()) savedTime.split(":")[0].toInt() else 18
            val minute = if (savedTime.isNotEmpty()) savedTime.split(":")[1].toInt() else 0
            
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText("Set Workout Reminder Time")
                .build()
            
            picker.addOnPositiveButtonClickListener {
                val newHour = picker.hour
                val newMinute = picker.minute
                val formattedTime = String.format("%02d:%02d", newHour, newMinute)
                binding.etWorkoutReminder.setText(formattedTime)
                viewModel.saveWorkoutReminderTime(formattedTime)
            }
            
            picker.show(parentFragmentManager, "workout_reminder_picker")
        }
    }
    
    private fun loadUserPreferences() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Load theme setting
            val themeMode = viewModel.themeModeFlow.first()
            val themePosition = when (themeMode) {
                "light" -> 0
                "dark" -> 1
                else -> 2 // system
            }
            binding.etTheme.setText(
                when (themePosition) {
                    0 -> "Light"
                    1 -> "Dark"
                    else -> "System Default"
                }
            )
            
            // Load units setting
            val isMetric = viewModel.unitsMetricFlow.first()
            binding.switchMetricUnits.isChecked = isMetric
            
            // Load notification settings
            val notificationsEnabled = viewModel.notificationEnabledFlow.first()
            binding.switchNotifications.isChecked = notificationsEnabled
            binding.layoutReminderSettings.visibility = if (notificationsEnabled) View.VISIBLE else View.GONE
            
            // Load water reminder interval
            val waterReminderInterval = viewModel.waterReminderIntervalFlow.first()
            val intervalText = when (waterReminderInterval) {
                30 -> "30 minutes"
                60 -> "1 hour"
                120 -> "2 hours"
                180 -> "3 hours"
                240 -> "4 hours"
                else -> "1 hour"
            }
            binding.etWaterReminder.setText(intervalText)
            
            // Load workout reminder time
            val workoutReminderTime = viewModel.workoutReminderTimeFlow.first()
            binding.etWorkoutReminder.setText(workoutReminderTime)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
