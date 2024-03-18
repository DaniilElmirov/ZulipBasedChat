package com.elmirov.course

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun Float.sp(context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics
    )

fun Int.dpToPix(context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, toFloat(), context.resources.displayMetrics
    )

fun View.getHeightWithMargins(): Int =
    measuredHeight + marginTop + marginBottom

fun View.getWidthWithMargins(): Int =
    measuredWidth + marginLeft + marginRight

fun View.layoutWithMargins(width: Int, height: Int) {
    val left = width + marginLeft
    val top = height + marginTop
    layout(left, top, left + measuredWidth, top + measuredHeight)
}