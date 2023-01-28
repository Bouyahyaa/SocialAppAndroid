package com.example.socialapp.feature_posts.domain.model

data class Story(
    val id: String,
    val picture: String?,
    val progress: Float = 0.00f,
    var isLoaded: Boolean = false,
)
