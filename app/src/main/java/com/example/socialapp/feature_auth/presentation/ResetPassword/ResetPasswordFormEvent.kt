package com.example.socialapp.feature_auth.presentation.ResetPassword

sealed class ResetPasswordFormEvent {
    data class PasswordChanged(val password: String) : ResetPasswordFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : ResetPasswordFormEvent()
    object Submit : ResetPasswordFormEvent()
}