package com.example.movie.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.domain.PopularMovieUseCase
import com.example.movie.factory.PopularMovieFactory
import kotlinx.android.synthetic.main.popular_movie_fragment.*
import tokopedia.app.abstraction.base.BaseFragment
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.repository.movie.MovieRepository
import tokopedia.app.data.repository.movie.MovieRepositoryImpl
import tokopedia.app.data.routes.NetworkServices
import tokopedia.app.network.Network

class PopularMovieFragment : BaseFragment() {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: PopularMovieUseCase
    private lateinit var viewModel: PopularMovieViewModel
    private lateinit var gridLayoutManager: GridLayoutManager

    //adapter
    private val movies = mutableListOf<Movie>()
    private val _adapter by lazy {
        PopularMovieAdapter(movies)
    }

    override fun contentView() = R.layout.popular_movie_fragment

    override fun initObservable() {
        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        val networkBuilder = Network
            .builder()
            .create(NetworkServices::class.java)

        //init repository
        repository = MovieRepositoryImpl(networkBuilder)

        //init usecase
        useCase = PopularMovieUseCase((repository))

        viewModel = ViewModelProviders
            .of(this, PopularMovieFactory(useCase))
            .get(PopularMovieViewModel::class.java)
    }

    override fun initView() {
        recyclerPopularMovie.apply {
            layoutManager = gridLayoutManager
            adapter = _adapter
        }

        viewModel.apply {
            movie.observe(viewLifecycleOwner, Observer { data ->
                showPopularMovie(data.resultsIntent)
            })
            errorMessage.observe(viewLifecycleOwner, showError())
        }
    }

    private fun showError(): Observer<String> {
        return Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPopularMovie(data: List<Movie>) {
        movies.apply {
            clear()
            addAll(data)
        }
        _adapter.notifyDataSetChanged()
    }

}