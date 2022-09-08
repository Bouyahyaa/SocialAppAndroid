package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import com.example.socialapp.feature_auth.util.AuthError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginResult>> =
        flow {
            val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
            val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

            if (emailError != null || passwordError != null) {
                val loginResult = LoginResult(
                    emailError = emailError,
                    passwordError = passwordError,
                    success = false,
                    message = "Field Error"
                )
                emit(Resource.Error<LoginResult>(
                    message = loginResult.message,
                    data = loginResult
                ))
            }

            val login = repository.login(
                email.trim(),
                password.trim(),
            )

            if (!login.success) {
                emit(Resource.Error<LoginResult>(
                    message = login.message,
                ))
            }

            emit(Resource.Success<LoginResult>(data = login))
        }
}