package com.example.socialapp.feature_auth.data.repository

import android.content.SharedPreferences
import com.example.socialapp.feature_auth.data.remote.AuthRemoteDataSource
import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.data.remote.request.TokenRequest
import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject
import com.example.socialapp.core.util.Result

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val sharedPreferences: SharedPreferences,
) : AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Result {
        val response = authRemoteDataSource.register(
            RegisterRequest(
                email = email,
                username = username,
                password = password,
                confirmPassword = confirmPassword
            )
        )

        return Result(
            success = response.success,
            message = response.message
        )
    }


    override suspend fun login(email: String, password: String): LoginResult {
        val response = authRemoteDataSource.login(
            LoginRequest(
                email = email,
                password = password
            )
        )

        if (response.success) {
            sharedPreferences.edit().putString("jwt", response.token).apply()
            sharedPreferences.edit().putString("userId", response.userId).apply()
        }
        return LoginResult(
            userId = response.userId,
            token = response.token,
            success = response.success,
            message = response.message
        )
    }

    override suspend fun confirmEmail(code: String, email: String): Result {
        val response = authRemoteDataSource.confirmEmail(
            request = TokenRequest(
                code = code
            ),
            email = email
        )

        return Result(
            success = response.success,
            message = response.message
        )
    }

    override suspend fun resendCode(email: String): Result {
        val response = authRemoteDataSource.resendCode(email = email)

        return Result(
            success = response.success,
            message = response.message
        )
    }

    override suspend fun authenticate() {
        return authRemoteDataSource.authenticate()
    }
}