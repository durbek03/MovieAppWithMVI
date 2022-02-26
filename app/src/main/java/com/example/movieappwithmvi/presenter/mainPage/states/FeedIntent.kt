package com.example.movieappwithmvi.presenter.mainPage.states

import com.example.movieappwithmvi.models.Movie

sealed class FeedIntent {
    object FetchMovies : FeedIntent()
    object FetchSavedMovies : FeedIntent()
}
