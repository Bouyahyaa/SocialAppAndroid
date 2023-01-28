package com.example.socialapp.feature_posts.data.repository

import android.util.Log
import com.example.socialapp.feature_posts.data.local.PictureLocalSource
import com.example.socialapp.feature_posts.data.mapper.toPicture
import com.example.socialapp.feature_posts.data.mapper.toPictureEntity
import com.example.socialapp.feature_posts.data.remote.PictureRemoteSource
import com.example.socialapp.feature_posts.data.remote.dto.PictureDto
import com.example.socialapp.feature_posts.domain.model.Picture
import com.example.socialapp.feature_posts.domain.repository.PictureRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val pictureLocalSource: PictureLocalSource,
    private val pictureRemoteSource: PictureRemoteSource,
) : PictureRepository {

    override suspend fun getPictures(
        query: String,
        fetchFromRemote: Boolean,
    ): List<Picture> {
        val localPictures = pictureLocalSource.getPictures(query)
        var remotePictures: List<PictureDto> = emptyList()

        val isDbEmpty = localPictures.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        Log.e("shouldJustLoadFromCache", "${localPictures.size}")
        Log.e("shouldJustLoadFromCache", query)
        Log.e("shouldJustLoadFromCache", "$fetchFromRemote")
        Log.e("shouldJustLoadFromCache", "$shouldJustLoadFromCache")

        if (!shouldJustLoadFromCache) {
            try {
                remotePictures = pictureRemoteSource.getPictures()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }

            remotePictures.let { pictures ->
                pictureLocalSource.clearPictures()
                pictures.map {
                    pictureLocalSource.insertPicture(it.toPictureEntity())
                }
            }
        }

        return pictureLocalSource.getPictures(query).map {
            it.toPicture()
        }
    }

    override suspend fun deletePictures(picture: Picture) {
        val pictureEntity = picture.toPictureEntity()
        pictureLocalSource.deletePicture(pictureEntity)
    }

    override suspend fun likePictures(picture: Picture) {
        val pictureEntity = picture.toPictureEntity()
        pictureLocalSource.updatePicture(pictureEntity)
    }

    override suspend fun seePictures(picture: Picture) {
        val pictureEntity = picture.toPictureEntity()
        pictureLocalSource.updatePicture(pictureEntity)
    }
}