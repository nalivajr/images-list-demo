package by.nalivajr.imagelistdemo.ui.tools

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.nalivajr.imagelistdemo.R

class ItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val cellMargin = context.resources.getDimensionPixelSize(R.dimen.cellMargin)
        outRect.set(cellMargin, cellMargin, cellMargin, cellMargin)
    }
}