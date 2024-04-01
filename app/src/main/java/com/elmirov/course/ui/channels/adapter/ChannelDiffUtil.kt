package com.elmirov.course.ui.channels.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elmirov.course.domain.Channel

class ChannelDiffUtil : DiffUtil.ItemCallback<Channel>() {
    override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean =
        oldItem == newItem
}