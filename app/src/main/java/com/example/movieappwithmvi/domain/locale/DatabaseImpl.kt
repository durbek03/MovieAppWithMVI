package com.example.movieappwithmvi.domain.locale

import com.example.movieappwithmvi.data.locale.AppDatabase
import com.example.movieappwithmvi.data.locale.RoomDao
import com.example.movieappwithmvi.models.Genre
import com.example.movieappwithmvi.models.Movie
import javax.inject.Inject

class DatabaseImpl @Inject constructor(val appDatabase: AppDatabase): DatabaseRepository {
    override fun addGenre(genre: Genre) {
        roomDao().addGenre(genre)
    }

    override fun addMovie(movie: Movie) {
        roomDao().addMovie(movie)
    }

    override suspend fun clearGenres() {
        roomDao().clearGenres()
    }

    override suspend fun clearMovies() {
        roomDao().clearMovies()
    }

    override suspend fun getGenres(): List<Genre> {
        return roomDao().getGenres()
    }

    override suspend fun getMovies(genre: String): List<Movie> {
        return roomDao().getMovies(genre)
    }

    fun roomDao(): RoomDao {
        return appDatabase.roomDao()
    }
}