package com.example.socialapp.feature_auth.data.repository

import android.content.SharedPreferences
import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.data.remote.AuthApi
import com.example.socialapp.feature_auth.data.remote.request.LoginRequest
import com.example.socialapp.feature_auth.data.remote.request.RegisterRequest
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences,
) : AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Resource<Unit> {
        return try {
            val response = api.register(
                RegisterRequest(
                    email = email,
                    username = username,
                    password = password,
                    confirmPassword = confirmPassword
                )
            )
            if (!response.success) {
                Resource.Error(message = response.message, null)
            } else {
                Resource.Success(Unit)
            }
        } catch (e: HttpException) {
            Resource.Error(message = "UnknownError")
        } catch (e: Exception) {
            Resource.Error(message = "UnknownError")
        }
    }


    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            val response = api.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )

            if (!response.success) {
                Resource.Error(message = response.message, null)
            } else {
                sharedPreferences.edit().putString("jwt", response.token).apply()
                sharedPreferences.edit().putString("userId", response.userId).apply()
                Resource.Success(Unit)
            }
        } catch (e: HttpException) {
            Resource.Error(message = "UnknownError")
        } catch (e: Exception) {
            Resource.Error(message = "UnknownError")
        }
    }

    override suspend fun authenticate(): Resource<Unit> {
        return try {
            api.authenticate()
            Resource.Success(Unit)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Resource.Error(message = "Unauthorized")
            } else {
                Resource.Error(message = "UnknownError")
            }
        } catch (e: Exception) {
            Resource.Error(message = "UnknownError")
        }
    }
}