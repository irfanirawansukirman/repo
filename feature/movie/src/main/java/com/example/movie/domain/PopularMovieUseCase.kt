package com.example.movie.domain

import tokopedia.app.abstraction.base.UseCase
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movies
import tokopedia.app.data.repository.movie.MovieRepository

open class PopularMovieUseCase(private val repository: MovieRepository): UseCase<ResultState<Movies>> {

    suspend fun get(): ResultState<Movies> {
        val populateMovie = repository.getPopularMovie()
        return if (populateMovie.isSuccessful) {
            ResultState.Success(populateMovie.body()!!)
        } else {
            ResultState.Error(MOVIE_ERROR)
        }
    }

    companion object {
        private const val MOVIE_ERROR = "Error brader"
    }
}