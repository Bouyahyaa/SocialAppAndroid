package com.example.socialapp.feature_auth.presentation.ResetPassword

data class ResetPasswordFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
)