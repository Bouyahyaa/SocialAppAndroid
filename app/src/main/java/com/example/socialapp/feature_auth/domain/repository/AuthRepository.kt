package com.example.socialapp.feature_auth.domain.repository

import com.example.socialapp.core.util.Resource

interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Resource<Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Resource<Unit>

    suspend fun authenticate(): Resource<Unit>
}