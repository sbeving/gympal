package com.example.gym_pal.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gym_pal.databinding.FragmentBmiCalculatorBinding
import java.text.DecimalFormat

class BmiCalculatorFragment : Fragment() {

    private var _binding: FragmentBmiCalculatorBinding? = null
    private val binding get() = _binding!!
    private val decimalFormat = DecimalFormat("#.#")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val heightStr = binding.etHeight.text.toString()
        val weightStr = binding.etWeight.text.toString()

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter both height and weight", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val height = heightStr.toFloat() / 100 // Convert cm to meters
            val weight = weightStr.toFloat()

            val bmi = weight / (height * height)
            displayResult(bmi)
        } catch (e: NumberFormatException) {
            Toast.makeText(requireContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayResult(bmi: Float) {
        binding.cardResult.visibility = View.VISIBLE
        binding.tvBmiValue.text = decimalFormat.format(bmi)

        // Determine BMI category and description
        val (category, description) = when {
            bmi < 18.5 -> Pair("Underweight", "You are below the healthy weight range. Consider consulting with a healthcare provider.")
            bmi < 25 -> Pair("Normal Weight", "Your BMI is within a healthy range. Maintain your current lifestyle and diet.")
            bmi < 30 -> Pair("Overweight", "You are above the healthy weight range. Consider increasing physical activity.")
            else -> Pair("Obese", "Your BMI indicates obesity. Consider consulting with a healthcare provider.")
        }

        binding.tvBmiCategory.text = category
        binding.tvBmiDescription.text = description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
