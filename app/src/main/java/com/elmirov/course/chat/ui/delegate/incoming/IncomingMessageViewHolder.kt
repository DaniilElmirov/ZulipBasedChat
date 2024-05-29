package com.elmirov.course.chat.ui.delegate.incoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.databinding.IncomingMessageItemBinding

class IncomingMessageViewHolder(
    parent: ViewGroup,
    private val onMessageLongClick: (messageId: Int, timestamp: Int, messageText: String) -> Unit,
    private val onIconAddClick: (messageId: Int) -> Unit,
    private val onReactionClick: (messageId: Int, reaction: Reaction, selected: Boolean) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.incoming_message_item, parent, false)
) {

    private val binding = IncomingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply {
            incomingMessage.userName = message.authorName
            incomingMessage.messageText = message.text
            incomingMessage.setAvatar(message.avatarUrl)

            incomingMessage.setLongClickListener {
                onMessageLongClick(message.id, message.timestamp, message.text)
            }

            if (message.reactions.isEmpty()) {
                incomingMessage.removeAllReactions()
            } else {
                incomingMessage.addReactions(message.reactions)

                incomingMessage.onIconAddClick {
                    onIconAddClick(message.id)
                }

                incomingMessage.setOnReactionClick {
                    onReactionClick(message.id, it.reaction, it.isSelected)
                }
            }
        }
    }
}