package com.elmirov.course

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private companion object {
        private const val MARGIN = 10
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        var currentWidth = 0
        var currentHeight = 0
        var rowTop = paddingTop
        var rowWidth = paddingLeft

        val maxHeight = if (childCount > 0) {
            children.maxOf {
                it.measuredHeight
            }
        } else 0

        children.forEach { view ->
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        children.forEach { view ->
            if (rowWidth + view.measuredWidth + paddingRight >= maxWidth) {
                rowWidth = paddingLeft
                rowTop += maxHeight
                currentHeight += rowTop + maxHeight
            }
            view.apply {
                left = rowWidth
                top = rowTop
                right = rowWidth + measuredWidth
                bottom = top + measuredHeight
            }
            rowWidth += view.measuredWidth + MARGIN.dp(context)
            currentWidth = maxOf(currentWidth, rowWidth)
        }

        setMeasuredDimension(
            resolveSize(currentWidth, widthMeasureSpec),
            resolveSize(maxOf(maxHeight, currentHeight), heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        children.forEach {
            it.layout(
                it.left,
                it.top,
                it.right,
                it.bottom
            )
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ) //TODO заменить на MATCH_PARENT
}