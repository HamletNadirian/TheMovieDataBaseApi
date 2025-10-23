package android.rest.api.themoviedbapi.ui.theme.navigation

enum class Screen(val route: String) {
    HOME("home"),
    FAVORITE("favorite"),
    DETAILS("details/{movieId}") {
        fun createRoute(movieId: Int) = "details/$movieId"
    }
}