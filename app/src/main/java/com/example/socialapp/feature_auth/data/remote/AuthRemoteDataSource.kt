package com.example.socialapp.feature_auth.data.remote

import com.example.socialapp.core.util.Response
import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.data.remote.request.ResetPasswordRequest
import com.example.socialapp.feature_auth.data.remote.request.TokenRequest
import com.example.socialapp.feature_auth.data.remote.response.LoginResponse
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
) {
    suspend fun register(request: RegisterRequest): Response {
        return api.register(request)
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }

    suspend fun confirmEmail(request: TokenRequest, email: String): Response {
        return api.confirmEmail(request, email)
    }

    suspend fun resendCode(email: String): Response {
        return api.resendCode(email)
    }

    suspend fun forgetPassword(email: String): Response {
        return api.forgetPassword(email)
    }

    suspend fun verifyTokenPassword(request: TokenRequest, email: String): Response {
        return api.verifyTokenPassword(request, email)
    }

    suspend fun resetPassword(request: ResetPasswordRequest, email: String): Response {
        return api.resetPassword(request, email)
    }

    suspend fun authenticate() {
        return api.authenticate()
    }
}