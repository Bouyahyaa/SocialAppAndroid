package com.example.socialapp.feature_auth.presentation.confirmation

sealed class ConfirmationFormEvent {
    data class CodeChanged(val code: String) : ConfirmationFormEvent()
    object ResendCode : ConfirmationFormEvent()
    object Submit : ConfirmationFormEvent()
}