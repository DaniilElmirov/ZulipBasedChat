package com.elmirov.course.channels.ui.delegate.topic

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class TopicDelegate(
    private val onTopicClick: (Int, String) -> Unit
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