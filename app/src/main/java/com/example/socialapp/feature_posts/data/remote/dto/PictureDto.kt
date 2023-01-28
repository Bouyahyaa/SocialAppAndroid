package com.example.socialapp.feature_posts.data.remote.dto

data class PictureDto(
    val color: String?,
    val created_at: String?,
    val description: String?,
    val height: Int?,
    val id: String?,
    val likes: Int?,
    val updated_at: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
)