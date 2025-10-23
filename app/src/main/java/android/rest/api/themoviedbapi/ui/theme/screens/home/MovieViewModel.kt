package android.rest.api.themoviedbapi.ui.theme.screens.home

import android.rest.api.themoviedbapi.BuildConfig
import android.rest.api.themoviedbapi.domain.repository.MovieRepository
import android.rest.api.themoviedbapi.domain.model.Movie
import android.rest.api.themoviedbapi.domain.model.MovieDetails
import android.rest.api.themoviedbapi.data.remote.network.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    val favoriteMovies: StateFlow<List<Movie>> = repository.getFavoriteMovies()
        .stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails.asStateFlow()
    private val _isCurrentMovieFavorite = MutableStateFlow(false)
    private val apiKey = BuildConfig.TMDB_API_KEY

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPopularMovie(apiKey)
                var loadedMovies = response.results
                loadedMovies = loadedMovies.map { movie ->
                    movie.copy(isFavorite = repository.isMovieFavorite(movie.id))
                }
                _movies.value = loadedMovies
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieDetails.value = RetrofitInstance.api.getMovieDetails(movieId, apiKey)
                _isCurrentMovieFavorite.value = repository.isMovieFavorite(movieId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            val currentMovies = _movies.value.toMutableList()
            val movieIndex = currentMovies.indexOfFirst { it.id == movieId }

            if (movieIndex != -1) {
                val movie = currentMovies[movieIndex]
                repository.toggleFavorite(movie)
                currentMovies[movieIndex] = movie.copy(isFavorite = !movie.isFavorite)
                _movies.value = currentMovies
                _isCurrentMovieFavorite.value = !movie.isFavorite
            }
        }
    }

    fun refreshFavoriteStatus() {
        viewModelScope.launch {
            val updatedMovies = _movies.value.map { movie ->
                movie.copy(isFavorite = repository.isMovieFavorite(movie.id))
            }
            _movies.value = updatedMovies
        }
    }
}
