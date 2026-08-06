package com.example.challenge.data.api

import com.example.challenge.data.model.MovieDetail
import com.example.challenge.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    // Now Playing filmlerini çekmek için
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse

    // Popular filmlerini çekmek için
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse

    // Top Rated filmlerini çekmek için
    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse

    // Upcoming filmlerini çekmek için
    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse

    // Film ismine göre arama yapmak için
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse

    // Filmin tam detaylarını çekmek için
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetail
}