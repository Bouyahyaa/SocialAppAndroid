package com.example.socialapp.feature_auth.domain.repository

import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.core.util.Result

interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Result

    suspend fun login(
        email: String,
        password: String,
    ): LoginResult

    suspend fun confirmEmail(
        code: String,
        email: String,
    ): Result

    suspend fun resendCode(
        email: String,
    ): Result

    suspend fun forgetPassword(
        email: String,
    ): Result

    suspend fun verifyTokenPassword(
        code: String,
        email: String,
    ): Result

    suspend fun resetPassword(
        email: String,
        password: String,
        confirmPassword: String,
    ): Result

    suspend fun authenticate()
    suspend fun logout()
}