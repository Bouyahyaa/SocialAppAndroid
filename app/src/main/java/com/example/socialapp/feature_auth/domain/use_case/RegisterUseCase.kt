package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.data.remote.response.RegisterResponse
import com.example.socialapp.feature_auth.domain.models.RegisterResult
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
    ): Flow<Resource<RegisterResult>> = flow {
        try {
            emit(Resource.Loading<RegisterResult>())
            val response = repository.register(email, username, password, confirmPassword)
            if (!response.success) {
                emit(Resource.Error<RegisterResult>(message = response.message))
            } else {
                emit(Resource.Success<RegisterResult>(response))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RegisterResult>(e.localizedMessage
                ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<RegisterResult>("Couldn't reach server . Check your internet connection"))
        }
    }
}