package com.example.movieappwithmvi.useCases

import android.content.Context
import com.example.movieappwithmvi.constants.NetworkHelper
import com.example.movieappwithmvi.constants.Resource
import com.example.movieappwithmvi.domain.locale.DatabaseImpl
import com.example.movieappwithmvi.domain.locale.DatabaseRepository
import com.example.movieappwithmvi.domain.remote.ApiImpl
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    val networkHelper: NetworkHelper,
    val locale: DatabaseImpl,
    val remote: ApiImpl
) {
    fun getMovies(genre: String, page:Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            if (networkHelper.isOnline()) {
                try {
                    val movies = remote.getMovies(genre, page)
                    locale.clearMovies()
                    movies.forEach { locale.addMovie(it) }
                    emit(Resource.Success(data = movies))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.message()))
                }
            } else {
                val movies = locale.getMovies(genre)
                emit(Resource.Success(data = movies))
            }
        }
    }
}