package com.example.moviedetail.domain

import tokopedia.app.abstraction.base.UseCase
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.entity.Movies
import tokopedia.app.data.repository.movie_detail.MovieDetailRepository

open class MovieDetailUseCases(private val repository: MovieDetailRepository):
    UseCase<ResultState<Movie>> {

    suspend fun get(movieId: String): ResultState<Movie> {
        val populateMovie = repository.getMovieDetail(movieId)
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