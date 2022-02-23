package com.example.movieappwithmvi.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @ColumnInfo
    @SerializedName("genre")
    val genre: List<String>,
    @ColumnInfo
    @SerializedName("imageurl")
    val imageurl: List<String>,
    @SerializedName("imdbid")
    @PrimaryKey(autoGenerate = false)
    val imdbid: String,
    @ColumnInfo
    @SerializedName("imdbrating")
    val imdbrating: Double,
    @ColumnInfo
    @SerializedName("released")
    val released: Int,
    @ColumnInfo
    @SerializedName("synopsis")
    val synopsis: String,
    @ColumnInfo
    @SerializedName("title")
    val title: String,
    @ColumnInfo
    @SerializedName("type")
    val type: String
)