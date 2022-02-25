package com.example.movieappwithmvi.domain.locale

import com.example.movieappwithmvi.data.locale.AppDatabase
import com.example.movieappwithmvi.data.locale.RoomDao
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.SavedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class DatabaseImpl @Inject constructor(val appDatabase: AppDatabase): DatabaseRepository {

    override suspend fun saveMovie(savedMovies: SavedMovie) {
        roomDao().saveMovie(savedMovies)
    }

    override fun getSavedMovies(): Flow<List<SavedMovie>> = roomDao().getSavedMovies()

    override suspend fun unSaveMovie(id: String) {
        roomDao().unSaveMovie(id)
    }

    private fun roomDao(): RoomDao {
        return appDatabase.roomDao()
    }
}