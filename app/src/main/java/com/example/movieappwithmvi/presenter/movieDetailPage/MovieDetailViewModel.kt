package com.example.movieappwithmvi.presenter.movieDetailPage

import androidx.lifecycle.ViewModel
import com.example.movieappwithmvi.models.Movie
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class MovieDetailViewModel : ViewModel() {
    val isSaved = MutableStateFlow(false)
    val movie = MutableSharedFlow<Movie>()
}