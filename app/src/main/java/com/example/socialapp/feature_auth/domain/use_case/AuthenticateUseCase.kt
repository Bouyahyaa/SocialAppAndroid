package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): Resource<Unit> {
        return repository.authenticate()
    }
}