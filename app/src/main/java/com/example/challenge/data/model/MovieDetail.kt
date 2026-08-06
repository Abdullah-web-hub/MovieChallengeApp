package com.example.challenge.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String, // Film adı[cite: 1]
    @SerializedName("overview") val overview: String, // Açıklama[cite: 1]
    @SerializedName("poster_path") val posterPath: String?, // Film poster resmi[cite: 1]
    @SerializedName("release_date") val releaseDate: String?, // Çıkış tarihi[cite: 1]
    @SerializedName("vote_average") val voteAverage: Double, // Puan[cite: 1]
    @SerializedName("genres") val genres: List<Genre> // Türler[cite: 1]
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)