package android.rest.api.themoviedbapi.ui.theme.screens.home

import android.rest.api.themoviedbapi.ui.theme.screens.home.MovieViewModel
import android.rest.api.themoviedbapi.ui.theme.components.MovieCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun MovieScreen(navController: NavController) {

    val viewModel: MovieViewModel = hiltViewModel()
    val movies by viewModel.movies.collectAsState()
    val navBackStackEntry = navController.currentBackStackEntry

    LaunchedEffect(navBackStackEntry) {
        viewModel.refreshFavoriteStatus()
    }
    Scaffold { paddingValues ->
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClick = { navController.navigate("details/${movie.id}") },
                        onFavoriteClick = { viewModel.toggleFavorite(movie.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieScreenPreview() {
    MovieScreen(navController = rememberNavController())
}


