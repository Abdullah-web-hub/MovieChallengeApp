package com.example.challenge.data.repository

import com.example.challenge.data.api.TmdbApi
import com.example.challenge.data.model.MovieDetail
import com.example.challenge.data.model.MovieResponse
import com.example.challenge.domain.repository.MovieRepository
import com.example.challenge.util.Resource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun getNowPlaying(page: Int, apiKey: String): Resource<MovieResponse> {
        return try {
            val response = api.getNowPlaying(page, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu") // Hata yönetimi[cite: 1]
        }
    }

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): Resource<MovieDetail> {
        return try {
            val response = api.getMovieDetails(movieId, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }

    override suspend fun getPopular(page: Int, apiKey: String): Resource<MovieResponse> {
        return try {
            val response = api.getPopular(page, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }

    override suspend fun getTopRated(page: Int, apiKey: String): Resource<MovieResponse> {
        return try {
            val response = api.getTopRated(page, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }

    override suspend fun getUpcoming(page: Int, apiKey: String): Resource<MovieResponse> {
        return try {
            val response = api.getUpcoming(page, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }

    override suspend fun searchMovies(query: String, page: Int, apiKey: String): Resource<MovieResponse> {
        return try {
            val response = api.searchMovies(query, page, apiKey)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Bilinmeyen bir hata oluştu")
        }
    }
}