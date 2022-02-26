package com.example.movieappwithmvi.domain.remote

import com.example.movieappwithmvi.models.Movie

interface ApiRepository {
    suspend fun getMovies(page: Int, apiKey: String): List<Movie>
}