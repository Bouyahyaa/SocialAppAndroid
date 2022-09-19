package com.example.socialapp.feature_auth.presentation.verify_token_password

sealed class VerifyTokenPasswordFormEvent{
    data class CodeChanged(val code: String) : VerifyTokenPasswordFormEvent()
    object Submit : VerifyTokenPasswordFormEvent()
}