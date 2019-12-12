package tokopedia.app.data.repository.movie_detail

import retrofit2.Response
import tokopedia.app.data.entity.Movie

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): Response<Movie>
}