package com.elmirov.course.ui.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Message

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    private companion object {
        private const val INCOMING_MESSAGE = 0
        private const val OUTGOING_MESSAGE = 1

        private const val OTHER_ID = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INCOMING_MESSAGE -> IncomingMessageViewHolder(parent)
            OUTGOING_MESSAGE -> OutgoingMessageViewHolder(parent)
            else -> throw IllegalStateException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IncomingMessageViewHolder -> holder.bind(getItem(position))
            is OutgoingMessageViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).userId == OTHER_ID)
            INCOMING_MESSAGE
        else
            OUTGOING_MESSAGE
}