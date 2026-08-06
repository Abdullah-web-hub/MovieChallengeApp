package com.example.challenge.presentation.detail

import com.example.challenge.data.model.MovieDetail

data class DetailState(
    val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val error: String = ""
)