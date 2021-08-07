package com.example.sarycatalogtask.utils.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.AdapterView
import android.widget.ListPopupWindow
import android.widget.Spinner
import androidx.core.graphics.Insets
import androidx.core.view.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.lang.reflect.Field

/**
 * View view extension functions
 */
fun View.hide() = apply {
    this.visibility = View.GONE
}

fun View.show() = apply {
    this.visibility = View.VISIBLE
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun SwipeRefreshLayout.disableRefresh() = apply {
    this.isRefreshing = false
}

fun SwipeRefreshLayout.enableRefresh() = apply {
    this.isRefreshing = true
}

/**
 * for adding a custom calback unit for any spinne u want
 * @author Ahmed Hegzo
 * @param Nothing
 * @return Nothing
 */
fun Spinner.addSpinnerCallback(
    callbackOnItemSelected: ((
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) -> Unit),
    callbackOnNothingSelected: ((parent: AdapterView<*>?) -> Unit),
    callbackOnItemClick: ((
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) -> Unit)
) = apply {

    val onItemSelectedCallback = object : AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            callbackOnItemSelected.invoke(parent, view, position, id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            callbackOnNothingSelected.invoke(parent)
        }

        override fun onItemClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            callbackOnItemClick.invoke(parent, view, position, id)
        }
    }
    onItemSelectedListener = onItemSelectedCallback
}

/**
 * For handling the softkeyboard
 */
fun View?.fitSystemWindowsAndAdjustResize() = this?.let { view ->
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
        view.fitsSystemWindows = true
        val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        WindowInsetsCompat
            .Builder()
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(0, 0, 0, bottom)
            )
            .build()
            .apply {
                ViewCompat.onApplyWindowInsets(v, this)
            }
    }
}

/**
 * for setting customized height for spinner.
 */
fun Spinner.setDropDownHeight(height: Int) {
    val popup: Field = Spinner::class.java.getDeclaredField("mPopup")
    popup.isAccessible = true
    val popupWindow = popup[this] as ListPopupWindow
    popupWindow.height = height
}

/**
 * Because your layouts are so heavily nested, you need to recursively disable the views.
 * Instead of using your method, try something like this:
 */
fun View.changeEnableChildren(enable: Boolean) {
    isEnabled = enable
    (this as? ViewGroup)?.let {
        forEach {
            changeEnableChildren(enable)
        }
    }
}

fun View.collapse() = apply {
    val initialHeight: Int = measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                visibility = View.GONE
            } else {
                layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = (initialHeight / context.resources.displayMetrics.density).toLong()
    startAnimation(a)
}

fun View.expand(duration: Int = 350) = apply {
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight: Int = measuredHeight
    layoutParams.height = 1
    visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height =
                if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((targetHeight / context.resources.displayMetrics.density) + duration).toLong()
    startAnimation(a)
}

fun View.paddingLeft(padding: Int){
    setPadding(padding, paddingTop, paddingRight, paddingBottom)
}
fun View.paddingTop(padding: Int){
    setPadding(paddingLeft, padding, paddingRight, paddingBottom)
}
fun View.paddingBottom(padding: Int){
    setPadding(paddingLeft, paddingTop, paddingRight, padding)
}
fun View.paddingAll(padding: Int){
    setPadding(padding, padding, padding, padding)
}

fun View.marginTop(marginTop: Int){
    val params: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    layoutParams = params
}

fun View.marginBottom(marginBottom: Int){
    val params: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    layoutParams = params
}

fun View.marginLeft(marginLeft: Int){
    val params: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    layoutParams = params
}

fun View.marginRight(marginRight: Int){
    val params: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    layoutParams = params
}