package com.elmirov.course.channels.ui.delegate.channel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class ChannelDelegate(
    private val onChannelClick: (Int) -> Unit,
    private val onArrowClick: (Int) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ChannelViewHolder(
            parent = parent,
            onChannelClick = onChannelClick,
            onArrowClick = onArrowClick
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ChannelViewHolder).bind((item.content() as Channel))
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is ChannelDelegateItem
}