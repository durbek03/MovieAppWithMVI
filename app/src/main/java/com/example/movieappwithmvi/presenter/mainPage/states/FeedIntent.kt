package com.example.movieappwithmvi.presenter.mainPage.states

sealed class FeedIntent {
    object FetchGenres : FeedIntent()
    class FetchMovies(val genre: String) : FeedIntent()
}
