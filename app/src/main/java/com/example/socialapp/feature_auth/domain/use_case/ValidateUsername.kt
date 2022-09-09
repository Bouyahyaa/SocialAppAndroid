package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.feature_auth.domain.models.ValidationResult

class ValidateUsername {
    fun execute(username: String): ValidationResult {
        if (username.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = "The username needs to consist at least 3 characters"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}