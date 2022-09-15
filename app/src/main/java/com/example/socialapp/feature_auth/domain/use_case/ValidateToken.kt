package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.feature_auth.domain.models.ValidationResult
import javax.inject.Inject

class ValidateToken @Inject constructor() {
    fun execute(code: String): ValidationResult {

        if (code.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Code cannot be empty"
            )
        }

        if (code.length < 5) {
            return ValidationResult(
                successful = false,
                errorMessage = "Code needs to consist exactly 5 digits"
            )
        }

        val regex = Regex("[0-9]*")
        if (!regex.matches(code)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Code should have only digits"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}