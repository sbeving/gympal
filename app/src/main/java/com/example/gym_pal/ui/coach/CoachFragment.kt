package com.example.gym_pal.ui.coach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gym_pal.R
import com.example.gym_pal.databinding.FragmentCoachBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoachFragment : Fragment() {

    private var _binding: FragmentCoachBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CoachViewModel by viewModels()
    private lateinit var messageAdapter: ChatMessageAdapter
    
    // Predefined quick prompts for fitness advice
    private val quickPrompts = listOf(
        "Create a weekly workout plan for a beginner",
        "How can I improve my protein intake?",
        "Best exercises for core strength",
        "Tips for weight loss",
        "Proper form for squats"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoachBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupQuickPrompts()
        observeViewModel()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        messageAdapter = ChatMessageAdapter()  // Make sure this adapter is properly imported
        binding.rvMessages.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true // Messages appear from bottom to top
                reverseLayout = false // Newest messages at bottom
            }
        }
    }
    
    private fun setupQuickPrompts() {
        binding.quickPromptChipGroup.removeAllViews()
        
        // Add quick prompt chips
        quickPrompts.forEach { promptText ->
            val chip = layoutInflater.inflate(
                R.layout.item_quick_prompt_chip,
                binding.quickPromptChipGroup,
                false
            ) as Chip
            
            chip.text = promptText
            chip.setOnClickListener {
                // Send the prompt text when clicked
                sendMessage(promptText)
            }
            
            binding.quickPromptChipGroup.addView(chip)
        }
    }
    
    private fun observeViewModel() {
        // Observe conversations list
        viewModel.conversations.observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages)
            if (messages.isNotEmpty()) {
                // Scroll to the bottom to show newest message
                binding.rvMessages.post {
                    binding.rvMessages.smoothScrollToPosition(messages.size - 1)
                }
                
                // Hide the quick prompt suggestions once conversation has started
                if (messages.size > 2) {
                    binding.quickPromptContainer.visibility = View.GONE
                }
            } else {
                // Show quick prompts when there are no messages
                binding.quickPromptContainer.visibility = View.VISIBLE
            }
        }
        
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSend.isEnabled = !isLoading
        }
        
        // Observe errors
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
            }
        }
        
        binding.btnClearHistory.setOnClickListener {
            viewModel.clearConversationHistory()
            binding.quickPromptContainer.visibility = View.VISIBLE
        }
        
        // Add close button click listener
        binding.btnClosePrompts.setOnClickListener {
            binding.quickPromptContainer.visibility = View.GONE
        }
    }
    
    private fun sendMessage(message: String) {
        viewModel.sendMessage(message)
        binding.etMessage.setText("") // Clear the input field
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
