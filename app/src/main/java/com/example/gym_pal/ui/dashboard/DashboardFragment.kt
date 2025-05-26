package com.example.gym_pal.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set welcome message with user name (for prototype we'll use a static name)
        binding.tvWelcome.text = getString(R.string.welcome, "Saleh")

        // Set up mock data for the dashboard
        setupMockData()

        // Set click listeners for quick action buttons
        binding.btnBmiCalculator.setOnClickListener {
            findNavController().navigate(R.id.bmiCalculatorFragment)
        }

        binding.btnTrackWater.setOnClickListener {
            findNavController().navigate(R.id.trackingFragment)
            Toast.makeText(requireContext(), "Water tracking selected", Toast.LENGTH_SHORT).show()
        }

        binding.btnTrackFood.setOnClickListener {
            findNavController().navigate(R.id.trackingFragment)
            Toast.makeText(requireContext(), "Food tracking selected", Toast.LENGTH_SHORT).show()
        }

        binding.btnCoach.setOnClickListener {
            findNavController().navigate(R.id.coachFragment)
        }
    }

    private fun setupMockData() {
        // Steps data
        binding.tvStepsValue.text = "5,678"
        binding.progressSteps.max = 10000
        binding.progressSteps.progress = 5678

        // Calories data
        binding.tvCaloriesValue.text = "1,250"
        binding.progressCalories.max = 2000
        binding.progressCalories.progress = 1250

        // Water data
        binding.tvWaterValue.text = "4"
        binding.progressWater.max = 8
        binding.progressWater.progress = 4

        // Workouts data
        binding.tvWorkoutsValue.text = "1"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
