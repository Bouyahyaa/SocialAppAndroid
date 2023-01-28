package com.example.socialapp.feature_posts.data.local

import javax.inject.Inject

class PictureLocalSource @Inject constructor(
    private val dao: UnsplashDao,
) {
    suspend fun getPictures(query: String) = dao.getPictures(query)

    suspend fun insertPicture(pictureEntity: PictureEntity) = dao.insertPicture(pictureEntity)

    suspend fun deletePicture(pictureEntity: PictureEntity) = dao.deletePicture(pictureEntity)

    suspend fun updatePicture(pictureEntity: PictureEntity) = dao.updatePicture(pictureEntity)

    suspend fun clearPictures() = dao.clearPictures()
}