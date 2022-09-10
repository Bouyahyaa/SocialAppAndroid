package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.data.remote.response.RegisterResponse
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Flow<Resource<RegisterResponse>> = flow {
        try {
            emit(Resource.Loading<RegisterResponse>())
            val response = repository.register(email, username, password, confirmPassword)
            if (!response.success) {
                emit(Resource.Error<RegisterResponse>(message = response.message))
            } else {
                emit(Resource.Success<RegisterResponse>(response))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RegisterResponse>(e.localizedMessage
                ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<RegisterResponse>("Couldn't reach server . Check your internet connection"))
        }
    }
}