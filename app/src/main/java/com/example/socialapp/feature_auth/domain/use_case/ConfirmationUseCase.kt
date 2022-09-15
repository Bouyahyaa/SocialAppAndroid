package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_auth.domain.models.TokenResult
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class ConfirmationUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        code: String,
        email: String,
    ): Flow<Resource<TokenResult>> = flow {
        try {
            emit(Resource.Loading<TokenResult>())
            val response = repository.confirmEmail(code, email)
            emit(Resource.Success<TokenResult>(response))
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val error = JSONObject(throwable.response()?.errorBody()!!.string())
                emit(Resource.Error<TokenResult>(message = error["message"] as String))
            } else {
                emit(Resource.Error<TokenResult>(message = "Couldn't reach server . Check your internet connection"))
            }
        }
    }
}