package com.elmirov.course.chat.ui.delegate.outgoing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.databinding.OutgoingMessageItemBinding

class OutgoingMessageViewHolder(
    parent: ViewGroup,
    private val onMessageLongClick: (messageId: Int, timestamp: Int) -> Unit,
    private val onIconAddClick: (messageId: Int) -> Unit,
    private val onReactionClick: (messageId: Int, reaction: Reaction, selected: Boolean) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message_item, parent, false)
) {

    private val binding = OutgoingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply {
            messageText.text = message.text

            outgoingMessage.setOnLongClickListener {
                onMessageLongClick(message.id, message.timestamp)
                true
            }

            if (message.reactions.isEmpty()) {
                reactions.removeAllViews()
            } else {
                reactions.addReactions(message.reactions)

                reactions.onIconAddClick {
                    onIconAddClick(message.id)
                }

                reactions.setOnReactionClick {
                    onReactionClick(message.id, it.reaction, it.isSelected)
                }
            }
        }
    }
}