package com.example.movieappwithmvi.presenter.mainPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieappwithmvi.constants.Resource
import com.example.movieappwithmvi.domain.remote.ApiRepository
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.mainPage.pagination.MoviePagingSource
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import com.example.movieappwithmvi.useCases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(val remote: ApiRepository) : ViewModel() {
    private val TAG = "DummyViewModel"

    private val _state = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val state = _state.asStateFlow()
    val intent = MutableSharedFlow<FeedIntent>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collectLatest { intent ->
                when (intent) {
                    is FeedIntent.FetchMovies -> {
                        val genre = intent.genre
                        _state.emit(FeedStates.Loading)
                        Pager(
                            PagingConfig(pageSize = 20)
                        ) {
                            MoviePagingSource(genre = genre, remote = remote)
                        }.flow.collectLatest {
                            Log.d(TAG, "handleIntent: collecting Movie")
                            _state.emit(FeedStates.MoviesFetched(movies = it))
                        }
                    }
                }
            }
        }
    }
}