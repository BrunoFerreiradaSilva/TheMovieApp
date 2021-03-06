package com.example.movieappferreira.apllication

import android.app.Application
import com.example.movieappferreira.database.MovieDataBase
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieDataBase.getDatabase(this, applicationScope)  }
    val repository by lazy { MovieRoomRepository(database.movieDao()) }
}