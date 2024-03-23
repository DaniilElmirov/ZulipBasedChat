package com.elmirov.course.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.MessageItemBinding
import com.elmirov.course.domain.Message
import kotlin.random.Random

class MessageViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
) {

    private val binding = MessageItemBinding.bind(itemView)

    private val emojis = intArrayOf(
        0x1f600,
        0x1f603,
        0x1f604,
        0x1f601,
        0x1f606,
        0x1f605,
        0x1f923,
        0x1f602,
    )

    fun bind(message: Message) {
        binding.apply {
            this.message.userName = message.authorName
            this.message.messageText = message.text
            this.message.setAvatar(R.drawable.ic_launcher_foreground)
            message.reactions.forEach {
                this.message.addReaction(String(Character.toChars(it.key)), it.value)
            }
            this.message.onIconAddClick {
                it.addReaction(getRandomEmoji(), getRandomCount())
            }
        }
    }

    private fun getRandomEmoji(): String {
        val emoji = emojis[Random.nextInt(0, emojis.size)]
        return String(Character.toChars(emoji))
    }

    private fun getRandomCount(): Int =
        Random.nextInt(1, 100)
}