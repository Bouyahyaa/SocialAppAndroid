package com.example.socialapp.feature_auth.domain.repository

import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.models.RegisterResult
import com.example.socialapp.feature_auth.domain.models.TokenResult

interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): RegisterResult

    suspend fun login(
        email: String,
        password: String,
    ): LoginResult

    suspend fun confirmEmail(
        code: String,
        email: String,
    ): TokenResult

    suspend fun authenticate()
}