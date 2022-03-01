package com.example.movieappwithmvi.presenter.searchFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.pagination.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val remote: ApiRepository) : ViewModel() {
    val searchText = MutableSharedFlow<String>()
    private val _movies = MutableSharedFlow<PagingData<Movie>>()
    val movies = _movies.asSharedFlow()

    init {
        handleSearch()
    }

    private fun handleSearch() {
        viewModelScope.launch {
            searchText.collectLatest { searchQuery ->
                val flow = Pager(
                    PagingConfig(pageSize = 20)
                ) {
                    MoviePagingSource(MoviePagingSource.QueryType.SEARCH, remote).also {
                        it.query = searchQuery
                    }
                }.flow.cachedIn(viewModelScope)
                flow.collect { paginatedMovie ->
                    _movies.emit(paginatedMovie)
                }
            }
        }
    }
}