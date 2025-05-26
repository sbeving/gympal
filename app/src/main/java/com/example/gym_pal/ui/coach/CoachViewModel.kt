package com.example.gym_pal.ui.coach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gym_pal.data.model.CoachConversation
import com.example.gym_pal.data.repository.CoachRepository
import com.example.gym_pal.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoachViewModel @Inject constructor(
    private val coachRepository: CoachRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    
    private val _conversations = MutableLiveData<List<CoachConversation>>(emptyList())
    val conversations: LiveData<List<CoachConversation>> = _conversations
    
    // Default user ID for conversations
    private val defaultUserId = "default_user1"
    
    init {
        // Load conversations using the default user ID
        loadConversations()
    }

    private fun loadConversations() {
        viewModelScope.launch {
            try {
                coachRepository.getConversationsForUser(defaultUserId).collect { conversations ->
                    _conversations.value = conversations
                }
            } catch (e: Exception) {
                _error.value = "Failed to load conversations: ${e.message}"
                _conversations.value = emptyList()
            }
        }
    }
    
    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                coachRepository.sendMessage(defaultUserId, message)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error sending message: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearConversationHistory() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                coachRepository.clearConversationHistory(defaultUserId)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to clear conversation history: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun getUserId(): LiveData<String> {
        return userPreferencesRepository.userIdFlow.asLiveData()
    }
    fun getUserName(): LiveData<String> {
        return userPreferencesRepository.userNameFlow.asLiveData()
    }
    fun saveUserName(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserName(name)
        }
    }
    fun saveUserId(userId: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserId(userId)
        }
    }
    fun getDefaultUserId(): String {
        return defaultUserId
    }
    fun setDefaultUserId(userId: String) {
        // This method is for demonstration; in a real app, you might not want to change the default user ID
        viewModelScope.launch {
            userPreferencesRepository.saveUserId(userId)
        }
    }
    fun getDefaultUserName(): String {
        return "GymPal User" // Default name for the prototype
    }
    fun setDefaultUserName(name: String) {
        // This method is for demonstration; in a real app, you might not want to change the default user name
        viewModelScope.launch {
            userPreferencesRepository.saveUserName(name)
        }
    }
}