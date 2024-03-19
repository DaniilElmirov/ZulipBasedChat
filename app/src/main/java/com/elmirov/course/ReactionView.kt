package com.elmirov.course

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes

class ReactionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : View(context, attributeSet, defStyle, defTheme) {

    private companion object {
        const val DEFAULT_COUNT = 1
        const val DEFAULT_EMOJI = "\uD83E\uDD28"
    }

    var reaction: String = DEFAULT_EMOJI
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
        setOnClickListener {
            isSelected = if (isSelected) {
                count--
                false
            } else {
                count++
                true
            }

            if (count == 0) {
                (parent as FlexBoxLayout).removeView(this)
            }
        }
    }

    private val textToDraw
        get() = "$reaction $count"

    private val textPaint = TextPaint().apply {
        color = context.getColor(R.color.reaction_text_color)
        textSize = 14f.sp(context)
    }

    private val textRect = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
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
}