package com.example.challenge.presentation.search

import com.example.challenge.data.model.Movie

data class SearchState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Movie> = emptyList(),
    val error: String = ""
)