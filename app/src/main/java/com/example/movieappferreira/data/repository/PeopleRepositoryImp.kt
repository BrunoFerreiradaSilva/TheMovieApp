package com.example.movieappferreira.data.repository

import com.example.movieappferreira.data.domain.PeopleRepository
import com.example.movieappferreira.model.People
import com.example.movieappferreira.rest.service.MovieService
import javax.inject.Inject

class PeopleRepositoryImp @Inject constructor(private val movieService: MovieService) : PeopleRepository {
    override suspend fun getPeopleDetails(peopleID: Int): People? {
        return try {
            movieService.getPeopleDetails(peopleID)
        } catch (e: RuntimeException) {
            null
        }
    }

    override suspend fun getPeopleMovieList(movieID: Int): MutableList<People> {
        return try {
            movieService.getPeopleMovie(movieID).cast
        } catch (e: RuntimeException) {
            mutableListOf()
        }
    }
}