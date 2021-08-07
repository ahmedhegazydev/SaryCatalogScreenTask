package com.example.sarycatalogtask.utils.extensions

import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sarycatalogtask.R
import kotlin.math.floor
import kotlin.math.roundToInt


fun RecyclerView.addViewTreeObserver(childWidth: Int, ){

    val gridLayoutManager = GridLayoutManager(context, 4)
    layoutManager = gridLayoutManager

    viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                val viewWidth: Int = measuredWidth
//                val cardViewWidth =
//                    context.resources.getDimension(childWidth)
                val newSpanCount =
                    floor((viewWidth / childWidth).toDouble()).toInt()
                gridLayoutManager.spanCount = newSpanCount
                gridLayoutManager.requestLayout()
            }
        })
}