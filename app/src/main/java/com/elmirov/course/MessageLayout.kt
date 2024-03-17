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

    private val avatar = binding.avatar
    private val name = binding.name
    private val messageText = binding.messageText
    private val reactions = binding.reactions

    var userName: String
        get() = name.text.toString()
        set(value) {
            name.text = value
        }

    var message: String
        get() = messageText.text.toString()
        set(value) {
            messageText.text = value
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        val maxChildWidth = if (childCount > 0) {
            children.maxOf {
                it.getWidthWithMargins()
            }
        } else 0

        children.forEach {
            measureChildWithMargins(
                it,
                widthMeasureSpec,
                currentWidth,
                heightMeasureSpec,
                currentHeight
            )
            currentHeight += it.getHeightWithMargins()
            currentWidth = avatar.getWidthWithMargins() + paddingLeft
        }

        val width = currentWidth + maxChildWidth
        val height = currentHeight + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentWidth = paddingLeft
        var currentHeight = paddingTop

        avatar.layoutWithMargins(currentWidth, currentHeight)
        currentWidth += avatar.getWidthWithMargins()

        name.layoutWithMargins(currentWidth, currentHeight)
        currentHeight += name.getHeightWithMargins()

        messageText.layoutWithMargins(currentWidth, currentHeight)
        currentHeight += messageText.getHeightWithMargins()

        reactions.layoutWithMargins(currentWidth, currentHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )


    fun addReaction(emoji: String, count: Int) {
        val reactionView = ReactionView(context).apply {
            reaction = emoji
            this.count = count
        }

        reactions.addView(reactionView)
    }

    fun onIconAddClick(listener: (MessageLayout) -> Unit) {
        reactions.onIconAddClick {
            listener.invoke(this@MessageLayout)
        }
    }

    fun setAvatar(resId: Int) {
        avatar.setImageResource(resId)
    }
}