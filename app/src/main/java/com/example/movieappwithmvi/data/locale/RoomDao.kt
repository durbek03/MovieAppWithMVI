package com.example.movieappwithmvi.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappwithmvi.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    //SavedMovies Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: Movie)

    @Query("select * from Movie")
    fun getSavedMovies(): Flow<List<Movie>>

    @Query("delete from Movie where id = :id")
    suspend fun unSaveMovie(id: Int)

    @Query("select * from Movie where id = :id")
    suspend fun checkIfExists(id: Int): Movie?
}