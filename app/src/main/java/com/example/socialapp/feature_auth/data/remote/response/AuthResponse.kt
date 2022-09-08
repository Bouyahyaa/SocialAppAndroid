package com.example.socialapp.feature_auth.data.remote.response

data class AuthResponse(
    val userId: String,
    val token: String,
    val success: Boolean,
    val message: String,
)