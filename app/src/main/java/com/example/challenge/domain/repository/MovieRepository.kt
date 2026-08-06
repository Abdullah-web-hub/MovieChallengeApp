package com.example.challenge.domain.repository

import com.example.challenge.data.model.MovieDetail
import com.example.challenge.data.model.MovieResponse
import com.example.challenge.util.Resource

interface MovieRepository {
    suspend fun getNowPlaying(page: Int, apiKey: String): Resource<MovieResponse>
    suspend fun getPopular(page: Int, apiKey: String): Resource<MovieResponse>
    suspend fun getTopRated(page: Int, apiKey: String): Resource<MovieResponse>
    suspend fun getUpcoming(page: Int, apiKey: String): Resource<MovieResponse>
    suspend fun searchMovies(query: String, page: Int, apiKey: String): Resource<MovieResponse>
    suspend fun getMovieDetails(movieId: Int, apiKey: String): Resource<MovieDetail>
}