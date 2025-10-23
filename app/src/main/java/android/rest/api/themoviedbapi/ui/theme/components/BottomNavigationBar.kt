package android.rest.api.themoviedbapi.ui.theme.components

import android.annotation.SuppressLint
import android.rest.api.themoviedbapi.ui.theme.navigation.NavigationItem
import android.rest.api.themoviedbapi.ui.theme.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color

@Composable
    @SuppressLint("NotConstructor")
    fun BottomNavigationBar(
        navController: NavController
    ) {
        val selectedNavigationIndex = rememberSaveable {
            mutableIntStateOf(0)
        }
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Filled.Home,
                route = Screen.HOME.route
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Filled.Favorite,
                route = Screen.FAVORITE.route
            )
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            navigationItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedNavigationIndex.value == index,
                    onClick = {
                        selectedNavigationIndex.value = index
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            color = if (index == selectedNavigationIndex.intValue)
                                Color.Black
                            else Color.Gray
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        indicatorColor = Color.White,
                    )
                )
            }
        }
    }

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}




