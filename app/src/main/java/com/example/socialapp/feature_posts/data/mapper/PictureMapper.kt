package com.example.socialapp.feature_posts.data.mapper

import com.example.socialapp.feature_posts.data.local.PictureEntity
import com.example.socialapp.feature_posts.data.remote.dto.PictureDto
import com.example.socialapp.feature_posts.domain.model.Picture

fun PictureEntity.toPicture(): Picture {
    return Picture(
        id = id,
        width = width,
        height = height,
        color = color,
        created_at = created_at,
        updated_at = updated_at,
        description = description,
        raw = raw,
        full = full,
        regular = regular,
        thumb = thumb,
        idUser = idUser,
        username = username,
        name = name,
        small = small,
        medium = medium,
        large = large,
        likes = likes,
        isLiked = isLiked,
        seen = seen
    )
}


fun Picture.toPictureEntity(): PictureEntity {
    return PictureEntity(
        id = id,
        width = width,
        height = height,
        color = color,
        created_at = created_at,
        updated_at = updated_at,
        description = description,
        raw = raw,
        full = full,
        regular = regular,
        thumb = thumb,
        idUser = idUser,
        username = username,
        name = name,
        small = small,
        medium = medium,
        large = large,
        likes = likes,
        isLiked = isLiked,
        seen = seen
    )
}


fun PictureDto.toPictureEntity(): PictureEntity {
    return PictureEntity(
        id = id!!,
        color = color,
        created_at = created_at,
        updated_at = updated_at,
        description = description ?: "No Description",
        width = width.toString(),
        height = height.toString(),
        likes = likes.toString(),
        /** User **/
        /** User **/
        idUser = user?.id,
        username = user?.username,
        name = user?.name,
        //Profile Image
        small = user?.profile_image?.small,
        medium = user?.profile_image?.medium,
        large = user?.profile_image?.large,
        /** Urls **/
        /** Urls **/
        raw = urls?.raw,
        full = urls?.full,
        regular = urls?.regular,
        thumb = urls?.thumb
    )
}