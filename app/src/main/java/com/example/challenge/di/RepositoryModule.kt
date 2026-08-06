package com.example.challenge.di

import com.example.challenge.data.api.TmdbApi
import com.example.challenge.data.repository.MovieRepositoryImpl
import com.example.challenge.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: TmdbApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}