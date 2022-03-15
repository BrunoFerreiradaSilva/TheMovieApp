package com.example.movieappferreira.application

import android.app.Application
import com.example.movieappferreira.database.MovieDataBase
import com.example.movieappferreira.rest.repository.MovieRoomRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MovieApplication : Application() {

}