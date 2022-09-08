package com.example.socialapp.feature_auth.domain.models

import com.example.socialapp.feature_auth.util.AuthError

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val success: Boolean,
    val message: String = "",
)
