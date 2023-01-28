package com.example.socialapp.di

import android.app.Application
import androidx.room.Room
import com.example.socialapp.core.util.Constants
import com.example.socialapp.feature_posts.data.local.PictureLocalSource
import com.example.socialapp.feature_posts.data.local.UnsplashDatabase
import com.example.socialapp.feature_posts.data.remote.PictureApi
import com.example.socialapp.feature_posts.data.remote.PictureRemoteSource
import com.example.socialapp.feature_posts.data.repository.PictureRepositoryImpl
import com.example.socialapp.feature_posts.domain.repository.PictureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {
    @Provides
    @Singleton
    fun providePictureApi(): PictureApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL_UNSPLASH)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PictureApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): UnsplashDatabase {
        return Room.databaseBuilder(
            app, UnsplashDatabase::class.java, UnsplashDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideUnsplashDao(db: UnsplashDatabase) = db.unsplashDao

    @Provides
    @Singleton
    fun providePictureRepository(
        pictureLocalSource: PictureLocalSource,
        pictureRemoteSource: PictureRemoteSource,
    ): PictureRepository {
        return PictureRepositoryImpl(pictureLocalSource, pictureRemoteSource)
    }
}