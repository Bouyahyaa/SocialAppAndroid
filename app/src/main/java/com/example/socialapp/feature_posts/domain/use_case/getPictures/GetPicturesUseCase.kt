package com.example.socialapp.feature_posts.domain.use_case.getPictures

import com.example.socialapp.core.util.Resource
import com.example.socialapp.feature_posts.domain.model.Picture
import com.example.socialapp.feature_posts.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    operator fun invoke(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Picture>>> = flow {
        try {
            if (fetchFromRemote) {
                emit(Resource.Loading<List<Picture>>())
            }
            val pictures = repository.getPictures(query, fetchFromRemote)
            emit(Resource.Success<List<Picture>>(pictures))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Picture>>(e.localizedMessage ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Picture>>("Couldn't reach server . Check your internet connection"))
        }
    }
}
