package com.example.movieappwithmvi.presenter.mainPage.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.models.Movie
import javax.inject.Inject

class MoviePagingSource (val remote: ApiRepository) : PagingSource<Int, Movie>() {
    private val TAG = "MoviePagingSource"
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d(TAG, "load: loading movies")
        val key = params.key ?: 1
        val pageSize = params.loadSize

        val movies = remote.getMovies(key)
        return if (!movies.isNullOrEmpty()) {
            val nextKey = if (movies.size < pageSize) null else key + 1
            val prevKey = if (key == 1) null else key - 1
            LoadResult.Page(data = movies, prevKey, nextKey)
        } else {
            LoadResult.Error(Throwable("smth went wrong"))
        }
    }
}