package com.elmirov.course.channels.ui.delegate.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.ChannelItemBinding
import com.elmirov.course.channels.domain.entity.Channel

class ChannelViewHolder(
    parent: ViewGroup,
    private val onArrowBottomClick: (Int) -> Unit,
    private val onArrowTopClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.channel_item, parent, false)
) {

    private val binding = ChannelItemBinding.bind(itemView)

    fun bind(channel: Channel) {
        binding.channelName.text = channel.name

        if (channel.topics.isNullOrEmpty()) {
            binding.arrowBottom.isVisible = true
            binding.arrowTop.isVisible = false
        } else {
            binding.arrowBottom.isVisible = false
            binding.arrowTop.isVisible = true
        }

        binding.arrowBottom.setOnClickListener {
            onArrowBottomClick(channel.id)
        }

        binding.arrowTop.setOnClickListener {
            onArrowTopClick(channel.id)
        }
    }
}