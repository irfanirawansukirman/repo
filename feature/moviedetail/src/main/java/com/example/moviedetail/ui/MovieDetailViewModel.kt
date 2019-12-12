package com.example.moviedetail.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.moviedetail.domain.MovieDetailUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movie

interface MovieDetailContract {
    fun getMovieDetail(movieId: String)
    fun setMovieDetail(movieId: String)
}

class MovieDetailViewModel(private val useCase: MovieDetailUseCases) : ViewModel(),
    MovieDetailContract {

    private val _movieId = MediatorLiveData<String>()

    private val _movie = MediatorLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        _movie.addSource(_movieId) {
            getMovieDetail(it)
        }
    }

    override fun getMovieDetail(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCase.get(movieId)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultState.Success -> _movie.value = response.data
                    is ResultState.Error -> _errorMessage.value = response.error
                }
            }
        }
    }

    override fun setMovieDetail(movieId: String) {
        _movieId.value = movieId
    }

}