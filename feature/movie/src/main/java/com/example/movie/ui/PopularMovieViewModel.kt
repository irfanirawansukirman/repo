package com.example.movie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.PopularMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movies

interface PopularMovieContract {
    fun getPopularMovie()
}

class PopularMovieViewModel(private val popularMovieUseCase: PopularMovieUseCase) : ViewModel(),
    PopularMovieContract {

    private val _movies = MutableLiveData<Movies>()
    val movie: LiveData<Movies>
        get() = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getPopularMovie()
    }

    override fun getPopularMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = popularMovieUseCase.get()
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResultState.Success -> _movies.value = response.data
                    is ResultState.Error -> _errorMessage.value = response.error
                }
            }
        }
    }

}