package com.example.movieappwithmvi.domain.locale

import com.example.movieappwithmvi.models.Movie
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun saveMovie(movie: Movie)
    fun getSavedMovies(): Flow<List<Movie>>
    suspend fun unSaveMovie(id: Int)
    suspend fun checkIfExists(id: Int): Boolean
}