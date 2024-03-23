package com.elmirov.course.ui.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Message

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    var messages: List<Message> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder(parent)

    override fun getItemCount(): Int =
        messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }
}