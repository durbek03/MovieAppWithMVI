package com.example.movieappwithmvi.domain.locale

import androidx.room.Insert
import androidx.room.Query
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.SavedMovie
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun saveMovie(savedMovies: SavedMovie)
    fun getSavedMovies(): Flow<List<SavedMovie>>
    suspend fun unSaveMovie(id: String)
}