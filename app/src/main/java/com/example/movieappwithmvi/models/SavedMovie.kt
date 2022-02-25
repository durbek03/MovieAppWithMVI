package com.example.movieappwithmvi.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedMovie(
    @ColumnInfo
    val genre: List<String>,
    @ColumnInfo
    val imageurl: List<String>,
    @PrimaryKey(autoGenerate = false)
    val imdbid: String,
    @ColumnInfo
    val imdbrating: Double,
    @ColumnInfo
    val released: Int,
    @ColumnInfo
    val synopsis: String,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val type: String
)