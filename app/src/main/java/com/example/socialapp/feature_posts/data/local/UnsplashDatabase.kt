package com.example.socialapp.feature_posts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.socialapp.feature_posts.data.local.PictureEntity
import com.example.socialapp.feature_posts.data.local.UnsplashDao

@Database(
    entities = [PictureEntity::class],
    version = 5
)

abstract class UnsplashDatabase : RoomDatabase() {
    abstract val unsplashDao: UnsplashDao

    companion object {
        const val DATABASE_NAME = "unsplash_db"
    }
}