package com.example.movieappwithmvi.domain.remote

import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.Movies

interface ApiRepository {
    suspend fun getMovies(page: Int, apiKey: String): List<Movie>

    suspend fun searchMovie(query: String, page: Int, apiKey: String): List<Movie>
}