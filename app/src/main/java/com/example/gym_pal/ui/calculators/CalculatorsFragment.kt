package com.example.gym_pal.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentCalculatorsBinding

class CalculatorsFragment : Fragment() {

    private var _binding: FragmentCalculatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenBmi.setOnClickListener {
            findNavController().navigate(R.id.bmiCalculatorFragment)
        }

        binding.btnOpenBmr.setOnClickListener {
            findNavController().navigate(R.id.bmrCalculatorFragment)
        }

        binding.btnOpenProtein.setOnClickListener {
            Toast.makeText(requireContext(), "Coming soon in future updates!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
