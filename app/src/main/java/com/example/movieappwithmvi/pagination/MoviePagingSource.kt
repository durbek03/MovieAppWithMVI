package com.example.movieappwithmvi.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieappwithmvi.constants.Constants
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.models.Movie

class MoviePagingSource (val queryType: QueryType, val remote: ApiRepository) : PagingSource<Int, Movie>() {
    var query = ""
    private val TAG = "MoviePagingSource"
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d(TAG, "load: loading movies")
        val key = params.key ?: 1

        val movies: List<Movie> = when (queryType) {
            is QueryType.GET -> {
                remote.getMovies(key, Constants.API_KEY)
            }
            is QueryType.SEARCH -> {
                remote.searchMovie(query, key, Constants.API_KEY)
            }
        }
        return if (!movies.isNullOrEmpty()) {
            val nextKey = if (key == 500) null else key + 1
            val prevKey = if (key == 1) null else key - 1
            LoadResult.Page(data = movies, prevKey, nextKey)
        } else {
            LoadResult.Error(Throwable("smth went wrong"))
        }
    }

    sealed class QueryType {
        object SEARCH : QueryType()
        object GET : QueryType()
    }
}