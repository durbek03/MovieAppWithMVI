package com.example.movieappwithmvi.constants

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun List<String>.toSeparatedStr(): String {
    var str = ""
    for (i in this.indices) {
        if (i != 0) {
            str += ", ${this[i]}"
        } else {
            str += this[i]
        }
    }
    return str
}