package com.elmirov.course.chat.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import com.elmirov.course.R
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.entity.ReactionParams
import com.elmirov.course.util.dpToPix

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
            8.dpToPix(context).toInt(),
            4.dpToPix(context).toInt(),
            8.dpToPix(context).toInt(),
            4.dpToPix(context).toInt()
        )
    }

    override fun addView(view: View) {
        super.addView(view, childCount - 1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        children.forEach { view ->
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        val maxHeight = if (childCount > 0) {
            children.maxOf {
                it.measuredHeight
            }
        } else 0
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        var currentWidth = 0
        var currentRowHeight = 0
        var rowWidth = paddingLeft

        children.forEach { view ->
            if (rowWidth + view.measuredWidth + paddingRight >= maxWidth) {
                rowWidth = paddingLeft
                currentRowHeight += maxHeight + MARGIN_TOP.dpToPix(context).toInt()
            }
            view.apply {
                left = rowWidth
                top = currentRowHeight
                right = rowWidth + measuredWidth
                bottom = currentRowHeight + measuredHeight
            }
            rowWidth += view.measuredWidth + MARGIN_RIGHT.dpToPix(context).toInt()
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

    fun addReactions(reactions: Map<Reaction, ReactionParams>) {
        removeAllViews()
        addView(addIcon)
        reactions.forEach {
            val reactionView = ReactionView(context).apply {
                reaction = it.key
                count = it.value.count
                isSelected = it.value.selected
            }

            addView(reactionView)
        }
    }

    fun setOnReactionClick(listener: (ReactionView) -> Unit) {
        children.forEach { view ->
            if (view is ReactionView) {
                view.onReactionClick(listener)
            }
        }
    }
}