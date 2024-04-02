package com.elmirov.course.ui.channels.delegate.topic

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Topic
import com.elmirov.course.ui.adapter.delegate.AdapterDelegate
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class TopicDelegate(
    private val onTopicClick: () -> Unit
): AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        TopicViewHolder(parent, onTopicClick)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as TopicViewHolder).bind((item.content() as Topic), position)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is TopicDelegateItem
}