package com.example.moviedetail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedetail.R
import com.example.moviedetail.domain.MovieDetailUseCases
import com.example.moviedetail.factory.MovieDetailFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import tokopedia.app.abstraction.base.BaseActivity
import tokopedia.app.abstraction.util.ext.load
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.repository.movie_detail.MovieDetailRepository
import tokopedia.app.data.repository.movie_detail.MovieDetailRepositoryImpl
import tokopedia.app.data.routes.NetworkServices
import tokopedia.app.network.Network

class MovieDetailActivity : BaseActivity() {

    private lateinit var repository: MovieDetailRepository
    private lateinit var useCase: MovieDetailUseCases
    private lateinit var viewModel: MovieDetailViewModel

    override fun contentView() = R.layout.activity_movie_detail

    override fun initView() {
        //get movie id
        intent?.data?.lastPathSegment?.let {
            viewModel.setMovieDetail(it)
        }

        viewModel.apply {
            errorMessage.observe(this@MovieDetailActivity, showError())
            movie.observe(this@MovieDetailActivity, Observer { data ->
                showMovieDetail(data)
            })
        }
    }

    override fun initObservable() {
        val networkBuilder = Network
            .builder()
            .create(NetworkServices::class.java)

        //init repository
        repository = MovieDetailRepositoryImpl(networkBuilder)

        //init usecase
        useCase = MovieDetailUseCases(repository)

        //init viewModel
        viewModel = ViewModelProviders
            .of(this, MovieDetailFactory(useCase))
            .get(MovieDetailViewModel::class.java)
    }

    private fun showError(): Observer<String> {
        return Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showToast(title: String) = Toast.makeText(this, title, Toast.LENGTH_SHORT).show()

    private fun showMovieDetail(movie: Movie) {
        imgBanner.load(movie.bannerUrl())
        imgPoster.load(movie.posterUrl())
        txtMovieName.text = movie.title
        txtYear.text = movie.releaseDate
        txtContent.text = movie.overview
        txtRating.text = movie.voteAverage.toString()
        txtVote.text = movie.voteCount.toString()
    }

}
