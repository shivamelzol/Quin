package com.example.quinstories.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val fullName: String,
    val email: String,val password: String
)