package com.elmirov.course.chat.ui.delegate.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.TopicChatItemBinding

class ChatTopicViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.topic_chat_item, parent, false)
) {

    private val binding = TopicChatItemBinding.bind(itemView)

    fun bind(name: String) {
        binding.name.text = name
    }
}