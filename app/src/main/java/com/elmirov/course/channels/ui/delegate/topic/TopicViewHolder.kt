package com.elmirov.course.channels.ui.delegate.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.databinding.TopicItemBinding

class TopicViewHolder(
    parent: ViewGroup,
    private val onTopicClick: (Int, String) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
) {

    private val binding = TopicItemBinding.bind(itemView)

    fun bind(topic: Topic, position: Int) {
        if (position % 2 == 0)
            binding.root.setBackgroundResource(R.color.even_topic)
        else
            binding.root.setBackgroundResource(R.color.odd_topic)

        binding.name.text = topic.name

        val messageCountFormat = itemView.context.getString(R.string.message_count_mes)
        binding.messageCount.text = String.format(messageCountFormat, topic.messageCount)

        binding.root.setOnClickListener {
            onTopicClick(topic.topicChannelId, topic.name)
        }
    }
}