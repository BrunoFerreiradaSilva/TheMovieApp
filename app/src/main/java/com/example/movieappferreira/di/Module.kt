package com.example.movieappferreira.di

import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.data.repository.MovieRepositoryImp
import com.example.movieappferreira.data.repository.PeopleRepositoryImp
import com.example.movieappferreira.model.Movie
import com.example.movieappferreira.rest.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    fun providesMovieRepository(movieService: MovieService): MovieRepository {
        return MovieRepositoryImp(movieService)
    }

    @Provides
    fun providesPeopleRepository(movieService: MovieService): PeopleRepository {
        return PeopleRepositoryImp(movieService)
    }

    @Provides
    fun providesRetrofit(): MovieService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }
}