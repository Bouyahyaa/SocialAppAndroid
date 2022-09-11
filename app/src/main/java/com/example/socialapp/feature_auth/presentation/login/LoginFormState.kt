package com.example.socialapp.feature_auth.presentation.login

data class LoginFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)