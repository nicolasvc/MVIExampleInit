package com.example.mviexampleinit.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class TopSpacinItemDecoration (private val padding:Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)
        outRect.top = padding
    }
}