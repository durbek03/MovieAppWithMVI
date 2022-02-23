package com.example.movieappwithmvi.presenter.mainPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappwithmvi.constants.Resource
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import com.example.movieappwithmvi.useCases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val getGenresUseCase: GetGenresUseCase) :
    ViewModel() {
    private val TAG = "FeedViewModel"
    
    val action = MutableSharedFlow<FeedIntent>()
    private val _state = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val state = _state.asStateFlow()

    init {
        handleIntent()
        viewModelScope.launch {
            action.emit(FeedIntent.FetchGenres)
        }
    }

    private fun fetchGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchResult = getGenresUseCase.getGenres()
            fetchResult.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = FeedStates.Loading
                    }
                    is Resource.Success -> {
                        _state.value = FeedStates.GenresFetched(it.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = FeedStates.Failed(it.message ?: "failed")
                    }
                }
            }
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            action.collectLatest {
                if (it is FeedIntent.FetchGenres) {
                    Log.d(TAG, "handleIntent: fetching Genres")
                    fetchGenres()
                }
            }
        }
    }
}