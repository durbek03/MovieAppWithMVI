package com.example.movieappwithmvi.presenter.mainPage.adapters

import android.content.Context
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.movieappwithmvi.R
import kotlin.math.abs

class CustomPageTransformer(val context: Context) : ViewPager2.PageTransformer {
    val nextItemVisiblePx = context.resources.getDimension(R.dimen.viewpager_next_item_visible)
    private val TAG = "CustomPageTransformer"
    override fun transformPage(page: View, position: Float) {
        page.translationX = -nextItemVisiblePx * position
        page.scaleY = 1 - (0.25f * abs(position))
    }
}