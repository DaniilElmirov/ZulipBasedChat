package com.elmirov.course.ui.chat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elmirov.course.domain.Message

class MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
        oldItem == newItem
}