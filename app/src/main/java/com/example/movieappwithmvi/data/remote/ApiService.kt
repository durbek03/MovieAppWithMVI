package com.example.movieappwithmvi.data.remote

import com.example.movieappwithmvi.models.Movies
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular/")
    suspend fun getMovies(
        @Query(value = "api_key") apiKey: String,
        @Query("page") page: Int
    ): Movies

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query(value = "api_key") apiKey: String,
        @Query("page") page: Int
    ): Movies
}