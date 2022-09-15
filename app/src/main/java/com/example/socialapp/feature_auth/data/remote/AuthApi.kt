package com.example.socialapp.feature_auth.data.remote

import com.example.socialapp.core.util.Response
import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.data.remote.request.TokenRequest
import com.example.socialapp.feature_auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("/api/users/register")
    suspend fun register(
        @Body request: RegisterRequest,
    ): Response

    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse

    @POST("/api/users/confirmation/{email}")
    suspend fun confirmEmail(
        @Body request: TokenRequest,
        @Path("email") email: String,
    ): Response

    @POST("/api/users/resendCode/{email}")
    suspend fun resendCode(
        @Path("email") email: String,
    ): Response

    @GET("/api/users/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = "http://192.168.68.110:8080/"
    }
}