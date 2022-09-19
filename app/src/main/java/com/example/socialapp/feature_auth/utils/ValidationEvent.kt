package com.example.socialapp.feature_auth.utils

sealed class ValidationEvent {
    data class Success(val message: String) : ValidationEvent()
    data class Error(val error: String) : ValidationEvent()
}