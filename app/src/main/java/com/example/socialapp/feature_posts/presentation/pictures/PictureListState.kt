package com.example.socialapp.feature_posts.presentation.pictures

import com.example.socialapp.feature_posts.domain.model.Picture


data class PictureListState(
    val isLoading: Boolean = false,
    val pictures: List<Picture> = emptyList(),
    val error: String = "",
    var searchQuery: String = "",
    val isRefreshing: Boolean = false,
)