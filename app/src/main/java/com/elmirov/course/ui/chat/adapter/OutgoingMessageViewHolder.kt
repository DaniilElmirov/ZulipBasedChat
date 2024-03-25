package com.elmirov.course.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.OutgoingMessageItemBinding
import com.elmirov.course.domain.Message
import com.elmirov.course.domain.Reaction

class OutgoingMessageViewHolder(
    parent: ViewGroup,
    private val onAddIconClick: (Int) -> Unit,
    private val onMessageLongClick: () -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message_item, parent, false)
) {

    private val binding = OutgoingMessageItemBinding.bind(itemView)

    fun bind(message: Message) {
        binding.apply {

            messageText.text = message.text

            outgoingMessage.setOnLongClickListener {
                onMessageLongClick()
                true
            }

            if (message.reactions.isNullOrEmpty()) {
                reactions.removeAddIcon()
            } else {
                val reactList = message.reactions.map {
                    Reaction(it.key, it.value)
                }
                reactions.addReactions(reactList)
                reactions.onIconAddClick {
                    onAddIconClick(message.id)
                }
            }
        }
    }
}