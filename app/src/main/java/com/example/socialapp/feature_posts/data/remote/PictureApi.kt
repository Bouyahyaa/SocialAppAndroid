package com.example.socialapp.feature_posts.data.remote

import com.example.socialapp.core.util.Constants
import com.example.socialapp.feature_posts.data.remote.dto.PictureDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureApi {
    @GET("photos/?client_id=${Constants.APP_ID}")
    suspend fun getPictures(
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int,
        @Query("order_by") order: String
    ): List<PictureDto>
}