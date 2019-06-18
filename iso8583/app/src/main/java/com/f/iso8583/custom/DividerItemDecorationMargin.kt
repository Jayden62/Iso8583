package com.f.iso8583.custom


import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView


class DividerItemDecorationMargin(var divider: Drawable, private var margin: Int) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + margin
        val right = parent.width - parent.paddingRight - margin

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}
