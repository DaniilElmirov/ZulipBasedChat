package com.elmirov.course.chat.ui.delegate.outgoing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class OutgoingMessageDelegate(
    private val onMessageLongClick: (messageId: Int, timestamp: Int) -> Unit,
    private val onIconAddClick: (messageId: Int) -> Unit,
    private val onReactionClick: (messageId: Int, reaction: Reaction, selected: Boolean) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OutgoingMessageViewHolder(
            parent = parent,
            onMessageLongClick = onMessageLongClick,
            onIconAddClick = onIconAddClick,
            onReactionClick = onReactionClick,
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as OutgoingMessageViewHolder).bind(message = (item.content() as Message))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is OutgoingMessageDelegateItem
}