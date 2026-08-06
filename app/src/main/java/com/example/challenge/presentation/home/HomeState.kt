package com.example.challenge.presentation.home

import com.example.challenge.data.model.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val error: String = ""
)