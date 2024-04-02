package com.elmirov.course.ui.channels.adapter.delegate.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.ChannelItemBinding
import com.elmirov.course.domain.Channel

class ChannelViewHolder(
    parent: ViewGroup,
    private val onArrowClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.channel_item, parent, false)
) {

    private val binding = ChannelItemBinding.bind(itemView)

    fun bind(channel: Channel) {
        binding.channelName.text = channel.name
        binding.arrowBottom.setOnClickListener {
            onArrowClick(channel.id)
        }
    }
}