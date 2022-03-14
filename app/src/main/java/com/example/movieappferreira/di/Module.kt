package com.example.movieappferreira.di

import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.data.repository.MovieRepositoryImp
import com.example.movieappferreira.data.repository.PeopleRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    fun providesMovieRepository(): MovieRepository {
        return MovieRepositoryImp()
    }

    @Provides
    fun providesPeopleRepository(): PeopleRepository {
        return PeopleRepositoryImp()
    }
}