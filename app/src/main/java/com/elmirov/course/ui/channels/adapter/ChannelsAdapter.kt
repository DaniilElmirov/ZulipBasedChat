package com.elmirov.course.ui.channels.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.elmirov.course.domain.Channel

class ChannelsAdapter : ListAdapter<Channel, ChannelViewHolder>(ChannelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder =
        ChannelViewHolder(parent)

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}