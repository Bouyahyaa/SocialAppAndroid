package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.models.LoginResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
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
            emit(Resource.Success<LoginResult>(response))
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val error = JSONObject(throwable.response()?.errorBody()!!.string())
                emit(Resource.Error<LoginResult>(message = error["message"] as String))
            } else {
                emit(Resource.Error<LoginResult>(message = "Couldn't reach server . Check your internet connection"))
            }
        }
    }
}