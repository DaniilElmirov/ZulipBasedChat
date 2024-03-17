package com.elmirov.course

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private companion object {
        const val MARGIN_RIGHT = 10
        const val MARGIN_TOP = 8
    }

    private val addIcon = ImageView(context, attributeSet, defStyle, defTheme).apply {
        setImageResource(R.drawable.icon_add)
        setBackgroundResource(R.drawable.icon_add_shape)
        setPadding(
            8.dp(context),
            4.dp(context),
            8.dp(context),
            4.dp(context)
        )
    }

    init {
        addView(addIcon)
    }

    override fun addView(view: View) {
        super.addView(view, childCount - 1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxHeight = if (childCount > 0) {
            children.maxOf {
                it.measuredHeight
            } + MARGIN_TOP.dp(context)
        } else 0
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        var currentWidth = 0
        var currentRowHeight = 0
        var rowWidth = paddingLeft

        children.forEach { view ->
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        children.forEach { view ->
            if (rowWidth + view.measuredWidth + paddingRight >= maxWidth) {
                rowWidth = paddingLeft
                currentRowHeight += maxHeight
            }
            view.apply {
                left = rowWidth
                top = currentRowHeight
                right = rowWidth + measuredWidth
                bottom = currentRowHeight + measuredHeight
            }
            rowWidth += view.measuredWidth + MARGIN_RIGHT.dp(context)
            currentWidth = maxOf(currentWidth, rowWidth)
        }

        val currentHeight = currentRowHeight + maxHeight

        setMeasuredDimension(
            resolveSize(currentWidth, widthMeasureSpec),
            resolveSize(currentHeight, heightMeasureSpec)
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
        )

    fun onIconAddClick(listener: (FlexBoxLayout) -> Unit) {
        addIcon.setOnClickListener {
            listener.invoke(this@FlexBoxLayout)
        }
    }
}