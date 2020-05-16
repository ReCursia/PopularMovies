package com.recursia.popularmovies.presentation.views.decorations

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
        context: Context,
        marginLeft: Int,
        marginRight: Int,
        marginTop: Int,
        marginBottom: Int
) : RecyclerView.ItemDecoration() {
    private val marginLeft: Int
    private val marginRight: Int
    private val marginTop: Int
    private val marginBottom: Int

    init {
        val metrics = context.resources.displayMetrics
        this.marginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft.toFloat(), metrics).toInt()
        this.marginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginRight.toFloat(), metrics).toInt()
        this.marginTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop.toFloat(), metrics).toInt()
        this.marginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginBottom.toFloat(), metrics).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = marginLeft
        outRect.right = marginRight
        outRect.bottom = marginBottom
        outRect.top = marginTop
    }
}
