package com.elmirov.course.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.OutgoingMessageItemBinding
import com.elmirov.course.domain.Message
import kotlin.random.Random

class OutgoingMessageViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message_item, parent, false)
) {

    private val binding = OutgoingMessageItemBinding.bind(itemView)

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
            if (message.reactions.isNullOrEmpty()) {
                reactions.removeAddIcon()
            } else {
                message.reactions.forEach {
                    reactions.addReaction(String(Character.toChars(it.key)), it.value)
                }
                reactions.onIconAddClick {
                    it.addReaction(getRandomEmoji(), getRandomCount())
                }
            }
            messageText.text = message.text
        }
    }

    private fun getRandomEmoji(): String {
        val emoji = emojis[Random.nextInt(0, emojis.size)]
        return String(Character.toChars(emoji))
    }

    private fun getRandomCount(): Int =
        Random.nextInt(1, 100)
}