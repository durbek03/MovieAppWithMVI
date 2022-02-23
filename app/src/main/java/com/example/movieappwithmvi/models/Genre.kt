package com.example.movieappwithmvi.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    val genre: String
)

fun List<Genre>.toListString(): List<String> {
    return this.map { it.genre }
}