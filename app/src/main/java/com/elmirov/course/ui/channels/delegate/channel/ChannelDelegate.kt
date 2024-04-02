package com.elmirov.course.ui.channels.delegate.channel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.domain.Channel
import com.elmirov.course.ui.adapter.delegate.AdapterDelegate
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class ChannelDelegate(
    private val onArrowBottomClick: (Int) -> Unit,
    private val onArrowTopClick: (Int) -> Unit,
) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ChannelViewHolder(parent, onArrowBottomClick, onArrowTopClick)

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