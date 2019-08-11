package com.sma.socialmediaapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalDividerItemDecoration(private var space: Int, private var verticalOrientation: Boolean) :
    RecyclerView.ItemDecoration() {

    override
    fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // skip first item in the list
        if (parent.getChildAdapterPosition(view) != 0) {

            if (verticalOrientation) {

                outRect.set(space, 0, 0, 0)

            } else if (!verticalOrientation) {

                outRect.set(0, space, 0, 0)
            }
        }
    }
}