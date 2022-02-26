package com.example.movieappwithmvi.presenter.mainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieappwithmvi.domain.locale.DatabaseRepository
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.presenter.mainPage.pagination.MoviePagingSource
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(val api: ApiRepository, val database: DatabaseRepository) :
    ViewModel() {
    private val TAG = "FeedViewModel"

    val action = MutableSharedFlow<FeedIntent>()
    private val _movieState = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val movieState = _movieState.asStateFlow()

    private val _savedMovieState = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val savedMovieStates = _savedMovieState.asStateFlow()

    init {
        handleIntent()
        viewModelScope.launch {
            action.emit(FeedIntent.FetchMovies)
            action.emit(FeedIntent.FetchSavedMovies)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            action.collect {
                when (it) {
                    is FeedIntent.FetchMovies -> {
                        val flow = Pager(
                            PagingConfig(pageSize = 20)
                        ) {
                            MoviePagingSource(api)
                        }.flow.cachedIn(viewModelScope)
                        flow.collect { paginatedMovie ->
                            _movieState.emit(FeedStates.MoviesFetched(movies = paginatedMovie))
                        }
                    }
                    is FeedIntent.FetchSavedMovies -> {
                        database.getSavedMovies()
                            .collectLatest { savedMovies ->
                                _savedMovieState.emit(FeedStates.SavedMoviesFetched(movies = savedMovies))
                            }
                    }
                }
            }
        }
    }
}