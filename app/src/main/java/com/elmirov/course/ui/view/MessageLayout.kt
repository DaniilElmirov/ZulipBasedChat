package com.elmirov.course.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import com.elmirov.course.R
import com.elmirov.course.databinding.MessageLayoutBinding
import com.elmirov.course.domain.Reaction
import com.elmirov.course.util.dpToPix
import com.elmirov.course.util.getHeightWithMargins
import com.elmirov.course.util.getWidthWithMargins
import com.elmirov.course.util.layoutWithMargins

class MessageLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private val binding by lazy {
        MessageLayoutBinding.inflate(LayoutInflater.from(context), this)
    }

    private val avatar = binding.avatar
    private val name = binding.name
    private val message = binding.message
    private val reactions = binding.reactions

    private val correctionLeft = 12.dpToPix(context).toInt()
    private val correctionTop = 8.dpToPix(context).toInt()
    private val correctionRight = 4.dpToPix(context).toInt()
    private val correctionBottom = 20.dpToPix(context).toInt()

    var userName: String
        get() = name.text.toString()
        set(value) {
            name.text = value
        }

    var messageText: String
        get() = message.text.toString()
        set(value) {
            message.text = value
        }

    private val backgroundRect = RectF()
    private val backgroundPaint = Paint().apply {
        color = context.getColor(R.color.name_message_background_color)
    }

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        val maxChildWidth = if (childCount > 0) {
            children.maxOf {
                it.getWidthWithMargins()
            }
        } else 0

        measureChildWithMargins(
            avatar,
            widthMeasureSpec,
            currentWidth,
            heightMeasureSpec,
            currentHeight
        )
        currentWidth += avatar.getWidthWithMargins()
        currentHeight += avatar.getHeightWithMargins()

        val rectLeft = currentWidth
        val rectTop = paddingTop

        measureName(widthMeasureSpec, currentWidth, heightMeasureSpec, currentHeight)
        currentHeight += name.getHeightWithMargins()

        measureMessage(widthMeasureSpec, currentWidth, heightMeasureSpec, currentHeight)
        currentHeight += message.getHeightWithMargins()

        val rectRight = rectLeft + correctionLeft + correctionRight +
                maxOf(
                    message.getWidthWithMargins(),
                    name.getWidthWithMargins()
                )
        val rectBottom = rectTop + correctionTop + correctionBottom +
                message.getHeightWithMargins() + name.getHeightWithMargins()

        measureReactions(widthMeasureSpec, currentWidth, heightMeasureSpec, currentHeight)
        currentHeight += reactions.getHeightWithMargins()

        val width = currentWidth + maxChildWidth + correctionLeft + correctionRight
        val height = currentHeight + paddingBottom - correctionTop

        backgroundRect.set(
            rectLeft.toFloat(),
            rectTop.toFloat(),
            rectRight.toFloat(),
            rectBottom.toFloat()
        )

        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    private fun measureName(
        widthMeasureSpec: Int,
        currentWidth: Int,
        heightMeasureSpec: Int,
        currentHeight: Int
    ) {
        measureChildWithMargins(
            name,
            widthMeasureSpec,
            currentWidth + correctionLeft + correctionRight,
            heightMeasureSpec,
            currentHeight + correctionTop
        )
    }

    private fun measureMessage(
        widthMeasureSpec: Int,
        currentWidth: Int,
        heightMeasureSpec: Int,
        currentHeight: Int
    ) {
        measureChildWithMargins(
            message,
            widthMeasureSpec,
            currentWidth + correctionLeft + correctionRight,
            heightMeasureSpec,
            currentHeight + correctionBottom
        )
    }

    private fun measureReactions(
        widthMeasureSpec: Int,
        currentWidth: Int,
        heightMeasureSpec: Int,
        currentHeight: Int
    ) {
        measureChildWithMargins(
            reactions,
            widthMeasureSpec,
            currentWidth,
            heightMeasureSpec,
            currentHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        avatar.layoutWithMargins(currentWidth, currentHeight)
        currentWidth += avatar.getWidthWithMargins()

        name.layoutWithMargins(currentWidth + correctionLeft, currentHeight + correctionTop)
        currentHeight += name.getHeightWithMargins() + correctionTop

        message.layoutWithMargins(currentWidth + correctionLeft, currentHeight)
        currentHeight += message.getHeightWithMargins()

        reactions.layoutWithMargins(currentWidth, currentHeight + correctionBottom)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            backgroundRect,
            20.dpToPix(context),
            20.dpToPix(context),
            backgroundPaint
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

    fun addReactions(reactions: List<Reaction>) {
        this.reactions.addReactions(reactions)
    }

    fun onIconAddClick(listener: () -> Unit) {
        reactions.onIconAddClick {
            listener.invoke()
        }
    }

    fun setAvatar(resId: Int) {
        avatar.setImageResource(resId)
    }

    fun removeAllReactions() {
        reactions.removeAllViews()
    }

    fun setLongClickListener(listener: () -> Unit) {
        setOnLongClickListener {
            listener.invoke()
            true
        }
    }
}