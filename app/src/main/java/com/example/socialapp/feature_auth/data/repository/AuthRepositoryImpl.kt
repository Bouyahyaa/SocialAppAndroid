package com.example.socialapp.feature_auth.data.repository

import android.content.SharedPreferences
import com.example.socialapp.feature_auth.data.remote.AuthApi
import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.models.RegisterResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences,
) : AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): RegisterResult {
        val request = RegisterRequest(email, username, password, confirmPassword)
        val response = api.register(request)
        return RegisterResult(
            success = response.success,
            message = response.message
        )
    }

    override suspend fun login(email: String, password: String): LoginResult {
        val request = LoginRequest(email, password)
        val response = api.login(request)
        if (response.success) {
            sharedPreferences.edit()
                .putString("JWT_TOKEN", response.token)
                .putString("USER_ID", response.userId)
                .apply()
        }
        return LoginResult(
            success = response.success,
            message = response.message
        )
    }
}