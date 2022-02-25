package com.example.movieappwithmvi.domain.remote

import com.example.movieappwithmvi.data.remote.ApiService
import com.example.movieappwithmvi.models.Movie
import javax.inject.Inject

class ApiImpl @Inject constructor(val apiService: ApiService): ApiRepository {
    private val TAG = "ApiImpl"

    override suspend fun getMovies(page: Int): List<Movie> {
        return try {
            apiService.getMovies(page).results
        } catch (e: Exception) {
            emptyList()
        }
    }
}