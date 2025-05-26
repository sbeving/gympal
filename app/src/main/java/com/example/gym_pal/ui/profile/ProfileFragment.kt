package com.example.gym_pal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // In a real app, load profile data from repository/database
        // For now, just using mock data
        setupMockData()
        
        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile functionality will be added soon", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnEditGoals.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Goals functionality will be added soon", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnLogout.setOnClickListener {
            // In a real app, we would clear session/logout the user
            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun setupMockData() {
        // User profile data
        binding.tvUserName.text = "Saleh Touil"
        binding.tvUserEmail.text = "saleh.touil@icloud.com"
        binding.tvAge.text = "28"
        binding.tvGender.text = "Male"
        binding.tvHeight.text = "180 cm"
        binding.tvWeight.text = "75 kg"
        
        // Fitness goals data
        binding.tvStepsGoal.text = "10,000 steps"
        binding.tvWaterGoal.text = "8 glasses"
        binding.tvCalorieGoal.text = "2,000 calories"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
