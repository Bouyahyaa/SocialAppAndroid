package com.example.socialapp.feature_auth.data.remote.request

data class ResetPasswordRequest(
    val password: String,
    val confirmPassword: String,
)