package com.example.movieappwithmvi.constants

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.movieDetailPage.MovieDetailFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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