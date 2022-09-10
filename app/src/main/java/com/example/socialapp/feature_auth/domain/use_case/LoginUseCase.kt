package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<LoginResult>> = flow {
        try {
            emit(Resource.Loading<LoginResult>())
            val response = repository.login(email, password)
            if (!response.success) {
                emit(Resource.Error<LoginResult>(message = response.message))
            } else {
                emit(Resource.Success<LoginResult>(response))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<LoginResult>(e.localizedMessage
                ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<LoginResult>("Couldn't reach server . Check your internet connection"))
        }
    }
}