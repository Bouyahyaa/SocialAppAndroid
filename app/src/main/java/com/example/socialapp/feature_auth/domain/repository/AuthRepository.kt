package com.example.socialapp.feature_auth.domain.repository

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.data.remote.response.RegisterResponse

interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): RegisterResponse

    suspend fun login(
        email: String,
        password: String,
    ): Resource<Unit>

    suspend fun authenticate(): Resource<Unit>
}