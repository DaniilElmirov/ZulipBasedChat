package com.elmirov.course.ui.chat.delegate.incoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.IncomingMessageItemBinding
import com.elmirov.course.domain.entity.Message
import com.elmirov.course.domain.entity.Reaction

class IncomingMessageViewHolder(
    parent: ViewGroup,
    private val addReaction: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.incoming_message_item, parent, false)
) {

    private val binding = IncomingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply { //TODO вынести в MessageLayout
            incomingMessage.userName = message.authorName
            incomingMessage.messageText = message.text
            incomingMessage.setAvatar(R.drawable.ic_launcher_foreground)

            incomingMessage.setLongClickListener {
                addReaction(message.id)
            }

            if (message.reactions.isNullOrEmpty()) {
                incomingMessage.removeAllReactions()
            } else {
                val reactList = message.reactions.map {
                    Reaction(it.key, it.value)
                }
                incomingMessage.addReactions(reactList)
                incomingMessage.onIconAddClick {
                    addReaction(message.id)
                }
            }
        }
    }
}