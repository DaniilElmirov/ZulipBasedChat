package com.elmirov.course.chat.ui.delegate.topic

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class ChatTopicDelegate(
    private val onTopicClick: (topicName: String) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ChatTopicViewHolder(parent = parent, onTopicClick = onTopicClick)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ChatTopicViewHolder).bind(item.content() as String)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ChatTopicDelegateItem
}