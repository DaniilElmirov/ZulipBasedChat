package com.elmirov.course.chat.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.elmirov.course.R
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.util.dpToPix
import com.elmirov.course.util.sp

class ReactionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : View(context, attributeSet, defStyle, defTheme) {

    private companion object {
        const val DEFAULT_COUNT = 1

        const val DEFAULT_EMOJI_SIZE = 14f
    }

    var reaction: Reaction = Reaction("", "")
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    var count: Int = DEFAULT_COUNT
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    var countVisible: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    var size: Float = DEFAULT_EMOJI_SIZE
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.ReactionView) {
            getInt(R.styleable.ReactionView_count, DEFAULT_COUNT)
        }
        setBackgroundResource(R.drawable.reaction_bg)
        setPadding(
            8.dpToPix(context).toInt(),
            4.dpToPix(context).toInt(),
            8.dpToPix(context).toInt(),
            4.dpToPix(context).toInt()
        )
    }

    private val textToDraw
        get() = if (countVisible) "$reaction $count" else reaction.toString()

    private val textPaint = TextPaint().apply {
        color = context.getColor(R.color.text_100)
    }

    private val textRect = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.textSize = size.sp(context)
        textPaint.getTextBounds(textToDraw, 0, textToDraw.length, textRect)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + textRect.width(),
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + textRect.height(),
            heightMeasureSpec
        )

        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val topOffset = height / 2 - textRect.exactCenterY()

        canvas.drawText(textToDraw, paddingLeft.toFloat(), topOffset, textPaint)
    }

    fun onReactionClick(listener: (ReactionView) -> Unit) {
        setOnClickListener {
            listener(this)
        }
    }
}