package com.example.socialapp.feature_auth.presentation.register

data class RegistrationFormState(
    val isLoading: Boolean = false,
    val error: String = "",
    val username: String = "",
    val usernameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
)