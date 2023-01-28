package com.example.socialapp.feature_posts.domain.use_case.likePictures

import com.example.socialapp.feature_posts.domain.model.Picture
import com.example.socialapp.feature_posts.domain.repository.PictureRepository
import javax.inject.Inject

class LikePicturesUseCase @Inject constructor(
    private val repository: PictureRepository,
) {
    suspend operator fun invoke(picture: Picture) {
        repository.likePictures(picture = picture)
    }
}