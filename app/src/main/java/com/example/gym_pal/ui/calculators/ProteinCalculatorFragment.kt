package com.example.gym_pal.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentProteinCalculatorBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProteinCalculatorFragment : Fragment() {
    
    private var _binding: FragmentProteinCalculatorBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProteinCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupActivityLevelDropdown()
        setupGoalDropdown()
        
        binding.btnCalculate.setOnClickListener {
            calculateProteinNeeds()
        }
    }
    
    private fun setupActivityLevelDropdown() {
        val activityLevels = arrayOf(
            "Sedentary (little or no exercise)",
            "Light (exercise 1-3 days/week)",
            "Moderate (exercise 3-5 days/week)",
            "Active (exercise 6-7 days/week)",
            "Very Active (hard exercise daily or physical job)"
        )
        
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, activityLevels)
        (binding.tilActivity.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    
    private fun setupGoalDropdown() {
        val goals = arrayOf(
            "Weight Loss",
            "Maintenance",
            "Muscle Gain"
        )
        
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, goals)
        (binding.tilGoal.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    
    private fun calculateProteinNeeds() {
        // Get inputs
        val weight = binding.etWeight.text.toString().toFloatOrNull()
        val activityLevel = binding.etActivity.text.toString()
        val goal = binding.etGoal.text.toString()
        
        if (weight == null) {
            Snackbar.make(binding.root, "Please enter a valid weight", Snackbar.LENGTH_SHORT).show()
            return
        }
        
        // Base protein calculation (grams per kg of body weight)
        val proteinMultiplier = when (goal) {
            "Weight Loss" -> {
                when (activityLevel) {
                    "Sedentary (little or no exercise)" -> 1.2f
                    "Light (exercise 1-3 days/week)" -> 1.3f
                    "Moderate (exercise 3-5 days/week)" -> 1.4f
                    "Active (exercise 6-7 days/week)" -> 1.5f
                    "Very Active (hard exercise daily or physical job)" -> 1.6f
                    else -> 1.2f
                }
            }
            "Maintenance" -> {
                when (activityLevel) {
                    "Sedentary (little or no exercise)" -> 1.2f
                    "Light (exercise 1-3 days/week)" -> 1.3f
                    "Moderate (exercise 3-5 days/week)" -> 1.5f
                    "Active (exercise 6-7 days/week)" -> 1.7f
                    "Very Active (hard exercise daily or physical job)" -> 1.9f
                    else -> 1.5f
                }
            }
            "Muscle Gain" -> {
                when (activityLevel) {
                    "Sedentary (little or no exercise)" -> 1.6f
                    "Light (exercise 1-3 days/week)" -> 1.7f
                    "Moderate (exercise 3-5 days/week)" -> 1.8f
                    "Active (exercise 6-7 days/week)" -> 2.0f
                    "Very Active (hard exercise daily or physical job)" -> 2.2f
                    else -> 1.8f
                }
            }
            else -> 1.5f
        }
        
        val proteinNeeds = weight * proteinMultiplier
        val proteinNeedsLower = (proteinNeeds * 0.9).toInt() // 10% lower range
        val proteinNeedsUpper = (proteinNeeds * 1.1).toInt() // 10% upper range
        
        // Display result
        binding.cardResult.visibility = View.VISIBLE
        binding.tvResultValue.text = "$proteinNeedsLower - $proteinNeedsUpper g/day"
        
        // Add some recommendations
        val recommendations = when (goal) {
            "Weight Loss" -> "For weight loss, distribute protein evenly throughout the day to maintain muscle mass while in a caloric deficit."
            "Maintenance" -> "For maintenance, ensure you're getting enough protein to repair and maintain muscle tissue."
            "Muscle Gain" -> "For muscle gain, consume protein within 30 minutes post-workout to optimize muscle protein synthesis."
            else -> ""
        }
        
        binding.tvResultDescription.text = recommendations
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
