package com.example.movieappferreira.di

import com.example.movieappferreira.data.MovieRepositoryImp
import com.example.movieappferreira.data.PeopleRepositoryImp
import com.example.movieappferreira.domain.MovieRepository
import com.example.movieappferreira.domain.PeopleRepository
import com.example.movieappferreira.model.People
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    fun providesMovieRepository(): MovieRepository{
        return MovieRepositoryImp()
    }

    @Provides
    fun providesPeopleRepository(): PeopleRepository{
        return PeopleRepositoryImp()
    }
}