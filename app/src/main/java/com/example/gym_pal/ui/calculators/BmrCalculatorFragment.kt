package com.example.gym_pal.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentBmrCalculatorBinding
import kotlin.math.roundToInt

class BmrCalculatorFragment : Fragment() {

    private var _binding: FragmentBmrCalculatorBinding? = null
    private val binding get() = _binding!!

    private val activityLevels = mapOf(
        "Sedentary (little or no exercise)" to 1.2,
        "Lightly active (light exercise 1-3 days/week)" to 1.375,
        "Moderately active (moderate exercise 3-5 days/week)" to 1.55,
        "Very active (hard exercise 6-7 days/week)" to 1.725,
        "Extra active (very hard exercise & physical job)" to 1.9
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmrCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivityLevelDropdown()

        binding.btnCalculate.setOnClickListener {
            calculateBMR()
        }
    }

    private fun setupActivityLevelDropdown() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            activityLevels.keys.toList()
        )
        binding.dropdownActivity.setAdapter(adapter)
        binding.dropdownActivity.setText(activityLevels.keys.first(), false)
    }

    private fun calculateBMR() {
        val heightStr = binding.etHeight.text.toString()
        val weightStr = binding.etWeight.text.toString()
        val ageStr = binding.etAge.text.toString()
        val activityLevel = binding.dropdownActivity.text.toString()
        val isMale = binding.rbMale.isChecked

        if (heightStr.isEmpty() || weightStr.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please fill in all fields",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        try {
            val height = heightStr.toFloat()
            val weight = weightStr.toFloat()
            val age = ageStr.toInt()
            val activityMultiplier = activityLevels[activityLevel] ?: 1.2

            // Calculate BMR using Mifflin-St Jeor Equation
            val bmr = if (isMale) {
                (10 * weight) + (6.25 * height) - (5 * age) + 5
            } else {
                (10 * weight) + (6.25 * height) - (5 * age) - 161
            }

            // Calculate daily calories with activity factor
            val dailyCalories = bmr * activityMultiplier

            displayResult(bmr.roundToInt(), dailyCalories.roundToInt())
        } catch (e: NumberFormatException) {
            Toast.makeText(
                requireContext(),
                "Please enter valid numbers",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun displayResult(bmr: Int, dailyCalories: Int) {
        binding.cardResult.visibility = View.VISIBLE
        binding.tvBmrValue.text = bmr.toString()
        binding.tvActivityCalories.text = "Daily calories with activity: ${dailyCalories}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
