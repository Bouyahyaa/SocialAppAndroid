package com.example.socialapp.feature_posts.data.local

import androidx.room.*

@Entity(tableName = "pictures")
data class PictureEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val width: String?,
    val height: String?,
    val color: String?,
    val created_at: String?,
    val updated_at: String?,
    @ColumnInfo(name = "description", defaultValue = "No Description") val description: String?,

    /** Urls **/
    val raw: String?,
    val full: String?,
    val regular: String?,
    val thumb: String?,

    /** User **/
    val idUser: String?,
    val username: String?,
    val name: String?,
    // ProfileImage
    val small: String?,
    val medium: String?,
    val large: String?,

    val likes: String?,

    val isLiked: Boolean = false,

    val seen: Boolean = false,
) : java.io.Serializable