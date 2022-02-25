package com.example.movieappwithmvi.presenter.mainPage.states

import androidx.paging.PagingData
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.models.SavedMovie

sealed class FeedStates {
    class MoviesFetched(val movies: PagingData<Movie>) : FeedStates()
    class SavedMoviesFetched(val movies: List<SavedMovie>) : FeedStates()
    class Failed(message: String) : FeedStates()
    object Loading : FeedStates()
}
