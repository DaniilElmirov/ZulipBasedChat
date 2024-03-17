package com.elmirov.course

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import com.elmirov.course.databinding.MessageLayoutBinding

class MessageLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private val binding by lazy {
        MessageLayoutBinding.inflate(LayoutInflater.from(context), this)
    }

    var userName: String
        get() = binding.name.text.toString()
        set(value) {
            binding.name.text = value
        }

    var messageText: String
        get() = binding.message.text.toString()
        set(value) {
            binding.message.text = value
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        val maxChildWidth = if (childCount > 0) {
            children.maxOf {
                it.getWidthWithMargins()
            }
        } else
            0

        children.forEach {
            measureChildWithMargins(
                it,
                widthMeasureSpec,
                currentWidth,
                heightMeasureSpec,
                currentHeight
            )
            currentHeight += it.getHeightWithMargins()
            currentWidth = binding.avatar.getWidthWithMargins() + paddingLeft
        }

        val width = currentWidth + maxChildWidth
        val height = currentHeight + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        binding.apply {
            avatar.layoutWithMargins(currentWidth, currentHeight)
            currentWidth += avatar.getWidthWithMargins()

            name.layoutWithMargins(currentWidth, currentHeight)
            currentHeight += name.getHeightWithMargins()

            message.layoutWithMargins(currentWidth, currentHeight)
            currentHeight += message.getHeightWithMargins()

            reactions.layoutWithMargins(currentWidth, currentHeight)
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
}