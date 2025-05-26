package com.example.gym_pal.data.repository

import com.example.gym_pal.data.local.UserDao
import com.example.gym_pal.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    // Get user by ID
    fun getUserById(userId: String): Flow<User> {
        return userDao.getUserById(userId)
    }
    
    // Register new user
    suspend fun registerUser(name: String, email: String, password: String): String {
        val userId = UUID.randomUUID().toString()
        val user = User(
            userId = userId,
            name = name,
            email = email
        )
        userDao.insertUser(user)
        return userId
    }
    
    // Login user
    suspend fun loginUser(email: String, password: String): User? {
        // In a real app, we would check credentials
        // For now, just fetch user by email
        return userDao.getUserByEmail(email)
    }
    
    // Update user profile
    suspend fun updateUserProfile(
        user: User,
        height: Float? = null,
        weight: Float? = null,
        age: Int? = null,
        gender: String? = null
    ) {
        val updatedUser = user.copy(
            height = height ?: user.height,
            weight = weight ?: user.weight,
            age = age ?: user.age,
            gender = gender ?: user.gender
        )
        userDao.updateUser(updatedUser)
    }
    
    // Update user goals
    suspend fun updateUserGoals(
        user: User,
        dailyStepGoal: Int? = null,
        dailyWaterGoal: Int? = null,
        dailyCalorieGoal: Int? = null
    ) {
        val updatedUser = user.copy(
            dailyStepGoal = dailyStepGoal ?: user.dailyStepGoal,
            dailyWaterGoal = dailyWaterGoal ?: user.dailyWaterGoal,
            dailyCalorieGoal = dailyCalorieGoal ?: user.dailyCalorieGoal
        )
        userDao.updateUser(updatedUser)
    }
}
