package com.example.socialapp.feature_auth.domain.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)