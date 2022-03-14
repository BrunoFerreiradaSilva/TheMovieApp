package com.example.movieappferreira.application

import android.app.Application
import com.example.movieappferreira.database.MovieDataBase
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MovieApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { MovieRoomRepository(database.movieDao()) }

}