package android.rest.api.themoviedbapi.domain.repository

import android.rest.api.themoviedbapi.data.local.dao.FavoriteMovieDao
import android.rest.api.themoviedbapi.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val dao: FavoriteMovieDao) {

    fun getFavoriteMovies(): Flow<List<Movie>> = dao.getAllFavoriteMovies()

    suspend fun toggleFavorite(movie: Movie){
        if(dao.isFavorite(movie.id)){
            dao.deleteMovie(movie)
        }else{
            dao.insertMovie(movie.copy(isFavorite = true))
        }
    }
    suspend fun isMovieFavorite(movieId: Int): Boolean = dao.isFavorite(movieId)

}