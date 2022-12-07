package com.cleo.myha.component

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OverlapDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view!!, parent, state!!)
        val itemPosition = parent.getChildAdapterPosition(view!!)
        if (itemPosition == 0) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(Companion.overlap, 0, 0, 0)
        }
    }

    companion object {
        // Following code from : http://stackoverflow.com/questions/27633454/how-to-overlap-items-in-linearlayoutmanager-recyclerview-like-stacking-cards
        private const val overlap = -100
    }
}
