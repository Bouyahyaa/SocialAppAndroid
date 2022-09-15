package com.example.socialapp.feature_auth.presentation.confirmation

data class ConfirmationFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val code: String = "",
    val codeError: String? = null,
)