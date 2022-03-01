package com.example.movieappwithmvi.presenter

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {
    val currentFragment = MutableLiveData<Fragment>()
    val appBarState = MutableStateFlow<AppBarState>(AppBarState.VISIBLE)

    fun setCurrentFragment(fragment: Fragment) {
        currentFragment.value = fragment
    }
}