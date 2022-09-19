package com.example.socialapp.feature_auth.presentation.forget_password

data class ForgetPasswordFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val email: String = "",
    val emailError: String? = null,
)