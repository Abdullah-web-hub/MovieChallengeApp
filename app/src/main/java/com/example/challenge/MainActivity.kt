package com.example.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.challenge.presentation.Screen
import com.example.challenge.presentation.detail.DetailScreen
import com.example.challenge.presentation.home.HomeScreen
import com.example.challenge.presentation.search.SearchScreen
import com.example.challenge.ui.theme.ChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    // Sayfaların gösterileceği ana çerçeve
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 1. Ana Sayfa
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.Detail.createRoute(movieId))
                                }
                            )
                        }

                        // 2. Arama Sayfası
                        composable(route = Screen.Search.route) {
                            SearchScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.Detail.createRoute(movieId))
                                }
                            )
                        }

                        // 3. Detay Sayfası
                        composable(route = Screen.Detail.route) {
                            DetailScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Alt Menü (Bottom Navigation) Tasarımı
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Screen.Home.route, Icons.Default.Home),
        BottomNavItem("Search", Screen.Search.route, Icons.Default.Search)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = currentRoute == item.route || currentRoute?.startsWith("detail_screen") == true && item.name == "Home",
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Alt menü öğeleri için basit bir veri sınıfı
data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)