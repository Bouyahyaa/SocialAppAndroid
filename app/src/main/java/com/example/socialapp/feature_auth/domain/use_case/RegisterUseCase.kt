package com.example.socialapp.feature_auth.domain.use_case

import android.util.Patterns
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.models.RegisterResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import com.example.socialapp.feature_auth.util.AuthError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Flow<Resource<RegisterResult>> = flow {

        try {

            emit(Resource.Loading<RegisterResult>())

            var emailError: AuthError? = null
            val trimmedEmail = email.trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailError = AuthError.InvalidEmail
            } else if (trimmedEmail.isBlank()) {
                emailError = AuthError.FieldEmpty
            }

            var passwordError: AuthError? = null
            val containsLettersAndDigits =
                password.any { it.isDigit() } && password.any { it.isLetter() }
            val capitalLettersInPassword = password.any { it.isUpperCase() }
            if (password.isBlank()) {
                passwordError = AuthError.FieldEmpty
            } else if (password.length < 8) {
                passwordError = AuthError.InputTooShort
            } else if (!containsLettersAndDigits || !capitalLettersInPassword) {
                passwordError = AuthError.InvalidPassword
            }

            var usernameError: AuthError? = null
            val trimmedUsername = username.trim()
            if (trimmedUsername.length < 3) {
                usernameError = AuthError.InputTooShort
            } else if (trimmedUsername.isBlank()) {
                usernameError = AuthError.FieldEmpty
            }

            if (emailError != null || usernameError != null || passwordError != null) {
                val registerResult = RegisterResult(
                    emailError = emailError,
                    usernameError = usernameError,
                    passwordError = passwordError,
                    success = false,
                    message = "Field Error"
                )
                emit(Resource.Error<RegisterResult>(
                    message = registerResult.message,
                    data = registerResult
                ))
            }

            val register = repository.register(
                email.trim(),
                username.trim(),
                password.trim(),
                confirmPassword.trim()
            )

            if (!register.success) {
                emit(Resource.Error<RegisterResult>(
                    message = register.message,
                ))
            }

            emit(Resource.Success<RegisterResult>(data = register))

        } catch (e: HttpException) {
            emit(Resource.Error<RegisterResult>(e.localizedMessage ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<RegisterResult>("Couldn't reach server . Check your internet connection"))
        }
    }
}