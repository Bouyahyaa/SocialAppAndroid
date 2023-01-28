package com.example.socialapp.feature_posts.presentation.stories

import com.example.socialapp.feature_posts.domain.model.Story


data class StoriesState(
    val stories: List<Story> = emptyList(),
)
