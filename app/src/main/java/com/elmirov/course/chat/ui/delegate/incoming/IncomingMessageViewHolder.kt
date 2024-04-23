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
    private val openReactions: (Int) -> Unit,
    private val onReactionClick: (Int, Reaction, Boolean) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.incoming_message_item, parent, false)
) {

    private val binding = IncomingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply { //TODO вынести в MessageLayout
            incomingMessage.userName = message.authorName
            incomingMessage.messageText = message.text
            incomingMessage.setAvatar(message.avatarUrl)

            incomingMessage.setLongClickListener {
                openReactions(message.id)
            }

            if (message.reactions.isEmpty()) {
                incomingMessage.removeAllReactions()
            } else {
                incomingMessage.addReactions(message.reactions)

                incomingMessage.onIconAddClick {
                    openReactions(message.id)
                }

                incomingMessage.setOnReactionClick {
                    onReactionClick(message.id, it.reaction, it.isSelected)
                }
            }
        }
    }
}