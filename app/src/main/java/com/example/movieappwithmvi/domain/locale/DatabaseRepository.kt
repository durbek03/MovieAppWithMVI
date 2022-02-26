package com.example.movieappwithmvi.domain.locale

import com.example.movieappwithmvi.models.SavedMovie
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun saveMovie(savedMovies: SavedMovie)
    fun getSavedMovies(): Flow<List<SavedMovie>>
    suspend fun unSaveMovie(id: Int)
}