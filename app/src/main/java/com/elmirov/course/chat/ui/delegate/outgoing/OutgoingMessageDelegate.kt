package com.elmirov.course.chat.ui.delegate.outgoing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class OutgoingMessageDelegate(
    private val addReaction: (Int) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OutgoingMessageViewHolder(parent, addReaction)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as OutgoingMessageViewHolder).bind(message = (item.content() as Message))
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is OutgoingMessageDelegateItem
}