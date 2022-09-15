package com.example.socialapp.feature_auth.data.remote

import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.data.remote.request.TokenRequest
import com.example.socialapp.feature_auth.data.remote.response.LoginResponse
import com.example.socialapp.feature_auth.data.remote.response.RegisterResponse
import com.example.socialapp.feature_auth.data.remote.response.TokenResponse
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
) {
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return api.register(request)
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }

    suspend fun confirmEmail(request: TokenRequest, email: String): TokenResponse {
        return api.confirmEmail(request, email)
    }

    suspend fun authenticate() {
        return api.authenticate()
    }
}