package com.example.socialapp.feature_auth.data.remote.response

data class LoginResponse(
    val userId: String,
    val token: String,
    val success: Boolean,
    val message: String,
)