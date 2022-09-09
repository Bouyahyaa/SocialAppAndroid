package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Resource<Unit> {
        return repository.register(
            email = email,
            password = password,
            username = username,
            confirmPassword = confirmPassword
        )
    }
}