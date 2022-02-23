package com.example.movieappwithmvi.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieappwithmvi.models.Genre
import com.example.movieappwithmvi.models.Movie

@Dao
interface RoomDao {
    @Insert
    fun addGenre(genre: Genre)

    @Insert
    fun addMovie(movie: Movie)

    @Query("select * from Genre")
    suspend fun getGenres(): List<Genre>

    @Query("select * from Movie where genre like :genre")
    suspend fun getMovies(genre: String): List<Movie>

    @Query("delete from Genre")
    suspend fun clearGenres()

    @Query("delete from Movie")
    suspend fun clearMovies()
}