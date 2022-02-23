package com.example.movieappwithmvi.domain.remote

import android.util.Log
import com.example.movieappwithmvi.data.remote.ApiService
import com.example.movieappwithmvi.models.Genre
import com.example.movieappwithmvi.models.Movie
import javax.inject.Inject

class ApiImpl @Inject constructor(val apiService: ApiService): ApiRepository {
    private val TAG = "ApiImpl"
    override suspend fun getGenres(): List<Genre> {
        return apiService.getGenres().toGenres()
    }

    override suspend fun getMovies(genre: String, page: Int): List<Movie> {
        Log.d(TAG, "getMovies: fetching movies")
        val response = apiService.getMovies(genre, page).results
        Log.d(TAG, "getMovies: ${response[0].title}")
        return response
    }

    fun List<String>.toGenres(): List<Genre> {
        val list = mutableListOf<Genre>()
        this.forEach {
            list.add(Genre(genre = it))
        }
        return list
    }
}