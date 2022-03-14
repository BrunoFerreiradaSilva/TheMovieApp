package com.example.movieappferreira.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappferreira.model.MovieDetails
import com.example.movieappferreira.model.MoviePopular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MovieDetails::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    private class MovieCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MovieDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(MovieCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}