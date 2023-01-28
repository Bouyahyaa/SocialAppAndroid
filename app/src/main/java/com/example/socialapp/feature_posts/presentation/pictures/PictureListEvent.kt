package com.example.socialapp.feature_posts.presentation.pictures

import com.example.socialapp.feature_posts.domain.model.Picture


sealed class PictureListEvent {
    data class OnSearchQueryChange(val query: String) : PictureListEvent()
    data class DeletePicture(val picture: Picture, val query: String) : PictureListEvent()
    data class LikePicture(val id: String) : PictureListEvent()
    data class SeenPicture(val id: String) : PictureListEvent()
    object Refresh : PictureListEvent()
    object LogOut : PictureListEvent()
}