package com.example.gym_pal.ui.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gym_pal.databinding.FragmentTrackingBinding
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TrackingFragment : Fragment() {

    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    
    private var selectedAmount = 250 // Default to medium glass (ml)
    private var waterIntake = 1000 // Mock data (ml)
    private var waterGoal = 2000 // Mock data (ml)
    private var glassesCount = 4 // Mock data (glasses)
    private var glassesGoal = 8 // Mock data (glasses)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDate()
        setupWaterTracking()
        setupTabLayout()

        // Select medium glass by default - using isSelected instead of isChecked
        binding.btnMediumGlass.isSelected = true
    }

    private fun setupDate() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        binding.tvTrackingDate.text = dateFormat.format(Date())
    }

    private fun setupWaterTracking() {
        updateWaterUI()

        // Set glass size selection listeners
        binding.btnSmallGlass.setOnClickListener { selectedAmount = 200 }
        binding.btnMediumGlass.setOnClickListener { selectedAmount = 250 }
        binding.btnLargeGlass.setOnClickListener { selectedAmount = 500 }

        // Add water button
        binding.btnAddWater.setOnClickListener {
            // Update water intake
            waterIntake += selectedAmount
            glassesCount = (waterIntake / 250) // Assuming 1 glass = 250ml
            
            updateWaterUI()
            Toast.makeText(requireContext(), "Added ${selectedAmount}ml of water", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateWaterUI() {
        binding.tvWaterCount.text = "$glassesCount / $glassesGoal"
        binding.tvWaterMl.text = "$waterIntake / $waterGoal ml"
        binding.progressWater.max = glassesGoal
        binding.progressWater.progress = glassesCount
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showWaterTracking()
                    1 -> showFoodTracking()
                    2 -> showStepsTracking()
                    3 -> showWorkoutsTracking()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Food tracking
        binding.btnAddMeal.setOnClickListener {
            Toast.makeText(requireContext(), "Food tracking coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showWaterTracking() {
        binding.layoutWaterTracking.visibility = View.VISIBLE
        binding.layoutFoodTracking.visibility = View.GONE
        binding.layoutStepsTracking.visibility = View.GONE
        binding.layoutWorkoutsTracking.visibility = View.GONE
    }

    private fun showFoodTracking() {
        binding.layoutWaterTracking.visibility = View.GONE
        binding.layoutFoodTracking.visibility = View.VISIBLE
        binding.layoutStepsTracking.visibility = View.GONE
        binding.layoutWorkoutsTracking.visibility = View.GONE
    }

    private fun showStepsTracking() {
        binding.layoutWaterTracking.visibility = View.GONE
        binding.layoutFoodTracking.visibility = View.GONE
        binding.layoutStepsTracking.visibility = View.VISIBLE
        binding.layoutWorkoutsTracking.visibility = View.GONE
    }

    private fun showWorkoutsTracking() {
        binding.layoutWaterTracking.visibility = View.GONE
        binding.layoutFoodTracking.visibility = View.GONE
        binding.layoutStepsTracking.visibility = View.GONE
        binding.layoutWorkoutsTracking.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
