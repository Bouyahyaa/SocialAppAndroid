package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val response = repository.authenticate()
            emit(Resource.Success<Unit>(response))
        } catch (e: HttpException) {
            if (e.code() == 401) {
                emit(Resource.Error<Unit>("Unauthorized"))
            } else {
                emit(Resource.Error<Unit>(e.localizedMessage
                    ?: "An unexpected error occur"))
            }
        } catch (e: IOException) {
            emit(Resource.Error<Unit>("Couldn't reach server . Check your internet connection"))
        }
    }
}