package com.example.movieappwithmvi.useCases

import com.example.movieappwithmvi.constants.NetworkHelper
import com.example.movieappwithmvi.constants.Resource
import com.example.movieappwithmvi.domain.locale.DatabaseImpl
import com.example.movieappwithmvi.domain.remote.ApiImpl
import com.example.movieappwithmvi.models.toListString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    val networkHelper: NetworkHelper,
    val remote: ApiImpl,
    val locale: DatabaseImpl
) {
    suspend fun getGenres(): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading())
            if (networkHelper.isOnline()) {
                try {
                    val genres = remote.getGenres()
                    locale.clearMovies()
                    genres.forEach { locale.addGenre(it) }
                    emit(Resource.Success(data = genres.toListString()))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.message()))
                }
            } else {
                val genres = locale.getGenres()
                emit(Resource.Success(data = genres.toListString()))
            }
        }
    }
}