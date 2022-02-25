package com.example.movieappwithmvi.data.remote

import com.example.movieappwithmvi.models.Movies
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "x-rapidapi-host: ott-details.p.rapidapi.com",
        "x-rapidapi-key: 37f60e0f28mshc0f871e06f7c3e4p1c9610jsn8767086cf81f"
    )
    @GET("advancedsearch?start_year=2015&end_year=2022&language=english&sort=latest&page=1")
    suspend fun getMovies(@Query("page") page: Int): Movies
}