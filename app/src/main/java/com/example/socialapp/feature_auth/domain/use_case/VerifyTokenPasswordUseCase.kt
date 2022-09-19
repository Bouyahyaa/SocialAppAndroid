package com.example.socialapp.feature_auth.domain.use_case

import com.example.socialapp.core.util.Resource
import com.example.socialapp.core.util.Result
import com.example.socialapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class VerifyTokenPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        code: String,
        email: String,
    ): Flow<Resource<Result>> = flow {
        try {
            emit(Resource.Loading<Result>())
            val response = repository.verifyTokenPassword(code, email)
            emit(Resource.Success<Result>(response))
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val error = JSONObject(throwable.response()?.errorBody()!!.string())
                emit(Resource.Error<Result>(message = error["message"] as String))
            } else {
                emit(Resource.Error<Result>(message = "Couldn't reach server . Check your internet connection"))
            }
        }
    }
}