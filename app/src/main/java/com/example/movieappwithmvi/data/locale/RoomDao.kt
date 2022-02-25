package com.example.movieappwithmvi.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieappwithmvi.models.SavedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    //SavedMovies Table
    @Insert
    suspend fun saveMovie(savedMovies: SavedMovie)

    @Query("select * from SavedMovie")
    fun getSavedMovies(): Flow<List<SavedMovie>>

    @Query("delete from SavedMovie where imdbid = :id")
    suspend fun unSaveMovie(id: String)
}