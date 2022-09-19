package com.example.socialapp.feature_auth.presentation.forget_password

sealed class ForgetPasswordFormEvent {
    data class EmailChanged(val email: String) : ForgetPasswordFormEvent()
    object Submit : ForgetPasswordFormEvent()
}