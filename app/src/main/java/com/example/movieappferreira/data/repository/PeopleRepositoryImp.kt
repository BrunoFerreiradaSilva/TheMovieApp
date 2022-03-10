package com.example.movieappferreira.data.repository

import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.retrofit.RetrofitInitializer

class PeopleRepositoryImp : PeopleRepository {
    override suspend fun getPeopleDetails(peopleID: Int): People? {
        return try {
            RetrofitInitializer.movieService.getPeopleDetails(peopleID)
        } catch (e: RuntimeException) {
            null
        }
    }

    override suspend fun getPeopleMovieList(movieID: Int): MutableList<People> {
        return try {
            RetrofitInitializer.movieService.getPeopleMovie(movieID).cast
        } catch (e: RuntimeException) {
            mutableListOf()
        }
    }
}