package com.example.movieappwithmvi.domain.locale

import com.example.movieappwithmvi.models.Genre
import com.example.movieappwithmvi.models.Movie

interface DatabaseRepository {
    fun addGenre(genre: Genre)

    fun addMovie(movie: Movie)

    suspend fun clearGenres()
    suspend fun clearMovies()

    suspend fun getGenres(): List<Genre>

    suspend fun getMovies(genre: String): List<Movie>
}