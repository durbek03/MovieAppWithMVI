package com.example.movieappwithmvi.domain.locale

import android.util.Log
import com.example.movieappwithmvi.data.locale.AppDatabase
import com.example.movieappwithmvi.data.locale.RoomDao
import com.example.movieappwithmvi.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseImpl @Inject constructor(val appDatabase: AppDatabase): DatabaseRepository {
    private val TAG = "DatabaseImpl"

    override fun saveMovie(movie: Movie) {
        Log.d(TAG, "saveMovie: saving movie")
        roomDao().saveMovie(movie)
    }

    override fun getSavedMovies(): Flow<List<Movie>> = roomDao().getSavedMovies()

    override suspend fun unSaveMovie(id: Int) {
        roomDao().unSaveMovie(id)
    }

    override suspend fun checkIfExists(id: Int): Boolean {
        val movie = roomDao().checkIfExists(id)
        return movie != null
    }

    private fun roomDao(): RoomDao {
        return appDatabase.roomDao()
    }
}