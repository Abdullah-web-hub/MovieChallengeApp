package com.example.challenge.presentation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object Detail : Screen("detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "detail_screen/$movieId"
    }
}