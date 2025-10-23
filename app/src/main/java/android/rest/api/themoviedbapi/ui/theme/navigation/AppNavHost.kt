package android.rest.api.themoviedbapi.ui.theme.navigation

import android.rest.api.themoviedbapi.ui.theme.components.BottomNavigationBar
import android.rest.api.themoviedbapi.ui.theme.screens.details.DetailsScreen
import android.rest.api.themoviedbapi.ui.theme.screens.favorites.FavoriteScreen
import android.rest.api.themoviedbapi.ui.theme.screens.home.MovieScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HOME.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.HOME.route) {

                MovieScreen(navController)
            }
            composable(Screen.FAVORITE.route){
                FavoriteScreen()
            }
            composable(
                Screen.DETAILS.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            )

            { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                DetailsScreen(
                    navController = navController,
                    movieId = movieId,
                )
            }
        }
    }
}

@Preview
@Composable
fun AppNavHostPreview() {
    AppNavHost()
}