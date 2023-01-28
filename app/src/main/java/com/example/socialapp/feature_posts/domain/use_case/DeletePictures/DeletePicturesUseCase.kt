package com.example.socialapp.feature_posts.domain.use_case.DeletePictures

import com.example.socialapp.feature_posts.domain.model.Picture
import com.example.socialapp.feature_posts.domain.repository.PictureRepository
import javax.inject.Inject

class DeletePicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    suspend operator fun invoke(picture: Picture) {
        repository.deletePictures(picture = picture)
    }
}