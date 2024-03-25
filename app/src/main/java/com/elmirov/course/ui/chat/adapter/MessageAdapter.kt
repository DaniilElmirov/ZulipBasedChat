package com.elmirov.course.ui.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Message

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        private const val INCOMING_MESSAGE = 0
        private const val OUTGOING_MESSAGE = 1

        private const val OWN_ID = 0
        private const val OTHER_ID = -1
    }

    var messages: List<Message> = listOf(
        Message(
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12, 0x1f600 to 12, 0x1f601 to 11)
        ),
        Message(
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f601 to 11)
        ),
        Message(
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            userId = OTHER_ID,
            data = "14.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = null
        ),
        Message(
            userId = OWN_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = null
        ),
        Message(
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "фывфывыфыфыфв",
            reactions = null
        ),
        Message(
            userId = OWN_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = null
        ),
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INCOMING_MESSAGE -> IncomingMessageViewHolder(parent)
            OUTGOING_MESSAGE -> OutgoingMessageViewHolder(parent)
            else -> throw IllegalStateException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IncomingMessageViewHolder -> holder.bind(messages[position])
            is OutgoingMessageViewHolder -> holder.bind(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (messages[position].userId == OTHER_ID)
            INCOMING_MESSAGE
        else
            OUTGOING_MESSAGE

    override fun getItemCount(): Int =
        messages.size
}