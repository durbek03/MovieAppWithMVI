package com.example.movieappwithmvi.presenter.savedPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappwithmvi.domain.locale.DatabaseRepository
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    val locale: DatabaseRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val savedMovies = _movies.asSharedFlow()
    val action = MutableSharedFlow<FeedIntent>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            action.collect {
                if (it is FeedIntent.FetchSavedMovies) {
                    locale.getSavedMovies().collectLatest { movies ->
                        _movies.emit(FeedStates.SavedMoviesFetched(movies = movies))
                    }
                }
            }
        }
    }
}