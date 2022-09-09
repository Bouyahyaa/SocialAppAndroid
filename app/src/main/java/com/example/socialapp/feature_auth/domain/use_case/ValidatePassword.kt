package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.feature_auth.domain.models.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist at least 8 characters"
            )
        }

        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}