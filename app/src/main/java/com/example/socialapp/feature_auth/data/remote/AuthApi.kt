package com.example.socialapp.feature_auth.data.remote

import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.data.remote.response.AuthResponse
import com.example.socialapp.feature_auth.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/users/register")
    suspend fun register(
        @Body request: RegisterRequest,
    ) : RegisterResponse

    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): AuthResponse

    @GET("/api/users/authenticate")
    suspend fun authenticate()
}