package android.rest.api.themoviedbapi.ui.theme.navigation

sealed class Screen(val route: String) {
    object HOME : Screen("home")
    object FAVORITE : Screen("favorite")

    object DETAILS : Screen("details/{movieId}") {
        fun createRoute(movieId: Int) = "details/$movieId"
    }
}