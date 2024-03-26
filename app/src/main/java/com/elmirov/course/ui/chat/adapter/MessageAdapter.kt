package com.elmirov.course.ui.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Message

class MessageAdapter(
    private val onAddIconClick: (Int) -> Unit,
    private val onMessageLongClick: (Int) -> Unit,
) : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    private companion object {
        private const val INCOMING_MESSAGE = 0
        private const val OUTGOING_MESSAGE = 1

        private const val OTHER_ID = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INCOMING_MESSAGE -> IncomingMessageViewHolder(
                parent,
                onAddIconClick,
                onMessageLongClick
            )

            OUTGOING_MESSAGE -> OutgoingMessageViewHolder(
                parent,
                onAddIconClick,
                onMessageLongClick
            )

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