package com.example.socialapp.feature_posts.domain.repository

import com.example.socialapp.feature_posts.domain.model.Picture

interface PictureRepository {
    suspend fun getPictures(query: String, fetchFromRemote: Boolean): List<Picture>
    suspend fun deletePictures(picture: Picture)
    suspend fun likePictures(picture: Picture)
    suspend fun seePictures(picture: Picture)
}