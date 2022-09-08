package com.example.socialapp.feature_auth.data.remote.request

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
    val confirmPassword: String,
)