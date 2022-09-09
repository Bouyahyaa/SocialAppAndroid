package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.feature_auth.domain.models.ValidationResult
import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor() {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}