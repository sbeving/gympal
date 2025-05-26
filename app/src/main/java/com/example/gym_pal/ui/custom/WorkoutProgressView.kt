package com.example.gym_pal.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.gym_pal.R
import com.example.gym_pal.databinding.ViewWorkoutProgressBinding

class WorkoutProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewWorkoutProgressBinding
    
    // Workout properties
    private var workoutName: String = ""
    private var workoutType: Int = 0
    private var workoutProgress: Int = 0
    private var workoutGoal: Int = 0
    private var workoutUnit: String = ""

    init {
        // Fix the binding inflation - use correct number of arguments
        val inflater = LayoutInflater.from(context)
        binding = ViewWorkoutProgressBinding.inflate(inflater, this)
        addView(binding.root)
        
        // Get custom attributes
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WorkoutProgressView)
            
            try {
                workoutName = typedArray.getString(R.styleable.WorkoutProgressView_workoutName) ?: "Workout"
                workoutType = typedArray.getInt(R.styleable.WorkoutProgressView_workoutType, 0)
                workoutProgress = typedArray.getInt(R.styleable.WorkoutProgressView_workoutProgress, 0)
                workoutGoal = typedArray.getInt(R.styleable.WorkoutProgressView_workoutGoal, 0)
                workoutUnit = typedArray.getString(R.styleable.WorkoutProgressView_workoutUnit) ?: ""
            } finally {
                typedArray.recycle()
            }
        }
        
        updateUI()
    }

    private fun updateUI() {
        binding.tvWorkoutName.text = workoutName
        binding.progressWorkout.max = workoutGoal
        binding.progressWorkout.progress = workoutProgress
        binding.tvWorkoutProgress.text = "$workoutProgress/$workoutGoal $workoutUnit"
        
        updateWorkoutIcon()
    }
    
    // Setters to update properties programmatically
    fun setWorkoutName(name: String) {
        workoutName = name
        binding.tvWorkoutName.text = name
    }
    
    fun setWorkoutType(type: Int) {
        workoutType = type
        updateWorkoutIcon()
    }
    
    fun setWorkoutProgress(progress: Int) {
        workoutProgress = progress
        binding.progressWorkout.progress = progress
        binding.tvWorkoutProgress.text = "$workoutProgress/$workoutGoal $workoutUnit"
    }
    
    fun setWorkoutGoal(goal: Int) {
        workoutGoal = goal
        binding.progressWorkout.max = goal
        binding.tvWorkoutProgress.text = "$workoutProgress/$workoutGoal $workoutUnit"
    }
    
    fun setWorkoutUnit(unit: String) {
        workoutUnit = unit
        binding.tvWorkoutProgress.text = "$workoutProgress/$workoutGoal $workoutUnit"
    }
    
    private fun updateWorkoutIcon() {
        val iconResource = when(workoutType) {
            0 -> R.drawable.ic_workout_cardio
            1 -> R.drawable.ic_workout_strength
            2 -> R.drawable.ic_workout_flexibility
            3 -> R.drawable.ic_workout_hiit
            else -> R.drawable.ic_workout_cardio
        }
        
        binding.ivWorkoutType.setImageDrawable(ContextCompat.getDrawable(context, iconResource))
    }
}
