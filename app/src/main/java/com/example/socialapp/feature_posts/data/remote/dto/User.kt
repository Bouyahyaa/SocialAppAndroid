package com.example.socialapp.feature_posts.data.remote.dto


data class User(
    val id: String,
    val name: String,
    val profile_image: ProfileImage,
    val username: String
)