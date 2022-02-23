package com.example.movieappwithmvi.presenter

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val currentFragment = MutableLiveData<Fragment>()

    fun setCurrentFragment(fragment: Fragment) {
        currentFragment.value = fragment
    }
}