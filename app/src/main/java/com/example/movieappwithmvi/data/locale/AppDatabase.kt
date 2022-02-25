package com.example.movieappwithmvi.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.SavedMovie

@TypeConverters(ListTypeConverter::class)
@Database(entities = [Movie::class, SavedMovie::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun roomDao(): RoomDao
}