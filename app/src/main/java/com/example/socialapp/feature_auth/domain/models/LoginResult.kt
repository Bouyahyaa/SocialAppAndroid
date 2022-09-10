package com.example.socialapp.feature_auth.domain.models

data class LoginResult(
    val userId: String? = null,
    val token: String? = null,
    val success: Boolean,
    val message: String,
)
