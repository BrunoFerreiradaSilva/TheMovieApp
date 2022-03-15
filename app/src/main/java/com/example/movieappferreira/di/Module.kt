package com.example.movieappferreira.di

import android.content.Context
import com.example.movieappferreira.base.Constants
import com.example.movieappferreira.data.domain.MovieRepository
import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.data.repository.MovieRepositoryImp
import com.example.movieappferreira.data.repository.PeopleRepositoryImp
import com.example.movieappferreira.database.MovieDAO
import com.example.movieappferreira.database.MovieDataBase
import com.example.movieappferreira.model.Movie
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import com.example.movieappferreira.rest.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
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

    @Provides
    fun providesMovieDAO(@ApplicationContext appContext:Context):MovieDAO{
        return MovieDataBase.getDatabase(appContext).movieDao()
    }

    @Provides
    fun providesRepository(movieDAO: MovieDAO):MovieRoomRepository{
       return MovieRoomRepository(movieDAO)
    }

}