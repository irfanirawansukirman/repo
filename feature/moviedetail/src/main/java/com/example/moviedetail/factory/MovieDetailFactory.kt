package com.example.moviedetail.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedetail.domain.MovieDetailUseCases
import com.example.moviedetail.ui.MovieDetailViewModel

class MovieDetailFactory(private val useCase: MovieDetailUseCases) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(useCase) as T
    }
}