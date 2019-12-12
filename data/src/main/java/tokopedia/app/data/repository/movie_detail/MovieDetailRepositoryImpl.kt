package tokopedia.app.data.repository.movie_detail

import retrofit2.Response
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.routes.NetworkServices

class MovieDetailRepositoryImpl(private val networkServices: NetworkServices) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: String): Response<Movie> {
        return networkServices.getMovieDetail(movieId)
    }

}