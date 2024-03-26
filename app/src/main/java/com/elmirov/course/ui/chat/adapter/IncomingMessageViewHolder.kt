package com.elmirov.course.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.IncomingMessageItemBinding
import com.elmirov.course.domain.Message
import com.elmirov.course.domain.Reaction

class IncomingMessageViewHolder(
    parent: ViewGroup,
    private val onAddIconClick: (Int) -> Unit,
    private val onMessageLongClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.incoming_message_item, parent, false)
) {

    private val binding = IncomingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply {
            incomingMessage.userName = message.authorName
            incomingMessage.messageText = message.text
            incomingMessage.setAvatar(R.drawable.ic_launcher_foreground)

            incomingMessage.setLongClickListener {
                onMessageLongClick(message.id)
            }

            if (message.reactions.isNullOrEmpty()) {
                incomingMessage.removeAddIcon()
            } else {
                val reactList = message.reactions.map {
                    Reaction(it.key, it.value)
                }
                incomingMessage.addReactions(reactList)
                incomingMessage.onIconAddClick {
                    onAddIconClick(message.id)
                }
            }
        }
    }
}