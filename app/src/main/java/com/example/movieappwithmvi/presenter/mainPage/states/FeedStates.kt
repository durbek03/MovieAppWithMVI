package com.example.movieappwithmvi.presenter.mainPage.states

import androidx.paging.PagingData
import com.example.movieappwithmvi.models.Movie

sealed class FeedStates {
    class MoviesFetched(val movies: PagingData<Movie>) : FeedStates()
    class SavedMoviesFetched(val movies: List<Movie>) : FeedStates()
    class Failed(message: String) : FeedStates()
    object Loading : FeedStates()
}
