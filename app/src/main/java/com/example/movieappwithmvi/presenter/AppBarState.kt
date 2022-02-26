package com.example.movieappwithmvi.presenter

sealed class AppBarState {
    object VISIBLE : AppBarState()
    object INVISIBLE : AppBarState()
}