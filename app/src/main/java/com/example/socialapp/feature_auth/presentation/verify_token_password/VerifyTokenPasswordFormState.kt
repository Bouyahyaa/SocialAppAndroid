package com.example.socialapp.feature_auth.presentation.verify_token_password

data class VerifyTokenPasswordFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val code: String = "",
    val codeError: String? = null,
)