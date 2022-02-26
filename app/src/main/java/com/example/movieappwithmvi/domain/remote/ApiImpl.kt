package com.example.movieappwithmvi.domain.remote

import android.util.Log
import com.example.movieappwithmvi.data.remote.ApiService
import com.example.movieappwithmvi.models.Movie
import javax.inject.Inject

class ApiImpl @Inject constructor(val apiService: ApiService): ApiRepository {
    private val TAG = "ApiImpl"

    override suspend fun getMovies(page: Int, apiKey:String): List<Movie> {
        return try {
            val result = apiService.getMovies(apiKey, page).results
            Log.d(TAG, "getMovies: ${result.toString()}")
            result
        } catch (e: Exception) {
            Log.d(TAG, "getMovies: ${e.message}")
            emptyList()
        }
    }
}