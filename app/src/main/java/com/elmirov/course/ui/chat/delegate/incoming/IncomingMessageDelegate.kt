package com.elmirov.course.ui.chat.delegate.incoming

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Message
import com.elmirov.course.ui.adapter.delegate.AdapterDelegate
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class IncomingMessageDelegate(
    private val addReaction: (Int) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        IncomingMessageViewHolder(parent, addReaction)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as IncomingMessageViewHolder).bind(item.content() as Message)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is IncomingMessageDelegateItem
}