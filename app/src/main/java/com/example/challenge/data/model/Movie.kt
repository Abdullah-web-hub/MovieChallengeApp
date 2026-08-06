package com.example.challenge.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String, // Film adı
    @SerializedName("overview") val overview: String, // Açıklama[cite: 1]
    @SerializedName("poster_path") val posterPath: String?, // Film poster resmi[cite: 1]
    @SerializedName("release_date") val releaseDate: String?, // Çıkış tarihi[cite: 1]
    @SerializedName("vote_average") val voteAverage: Double, // Puan[cite: 1]
    @SerializedName("genre_ids") val genreIds: List<Int>? // Türler[cite: 1]
)