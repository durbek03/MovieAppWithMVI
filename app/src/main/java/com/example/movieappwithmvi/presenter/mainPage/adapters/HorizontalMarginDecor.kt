package com.example.movieappwithmvi.presenter.mainPage.adapters

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginDecor(context: Context, @DimenRes horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
    }

}