package com.example.movieappwithmvi.domain.remote

import com.example.movieappwithmvi.models.Genre
import com.example.movieappwithmvi.models.Movie

interface ApiRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getMovies(genre: String, page: Int): List<Movie>
}