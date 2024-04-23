package com.elmirov.course.channels.ui.delegate.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.databinding.ChannelItemBinding

class ChannelViewHolder(
    parent: ViewGroup,
    private val showChannelTopics: (Int) -> Unit,
    private val closeChannelTopics: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.channel_item, parent, false)
) {

    private val binding = ChannelItemBinding.bind(itemView)

    fun bind(channel: Channel) {

        val channelNameFormat = itemView.context.getString(R.string.hashtag_with_stream_name)
        binding.channelName.text = String.format(channelNameFormat, channel.name)

        if (channel.expanded) {
            binding.arrowBottom.isVisible = false
            binding.arrowTop.isVisible = true

            binding.root.setOnClickListener {
                closeChannelTopics(channel.id)
            }
        } else {
            binding.arrowBottom.isVisible = true
            binding.arrowTop.isVisible = false

            binding.root.setOnClickListener {
                showChannelTopics(channel.id)
            }
        }
    }
}