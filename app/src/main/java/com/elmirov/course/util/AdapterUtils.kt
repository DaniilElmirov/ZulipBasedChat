package com.elmirov.course.util

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegateItem
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegateItem
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.ui.delegate.date.DateDelegateItem
import com.elmirov.course.chat.ui.delegate.incoming.IncomingMessageDelegateItem
import com.elmirov.course.chat.ui.delegate.outgoing.OutgoingMessageDelegateItem
import com.elmirov.course.chat.ui.delegate.topic.ChatTopicDelegateItem
import com.elmirov.course.core.adapter.delegate.DelegateItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val IN_MILLIS = 1000
private const val SECONDS_IN_DAY = 24 * 60 * 60

fun List<Message>.toDelegateItems(ownId: Int, withTopics: Boolean = false): List<DelegateItem> {

    val delegates = mutableListOf<DelegateItem>()
    val dates = mutableSetOf<String>()

    val sortedMessages = sortedBy { it.timestamp }

    sortedMessages.forEach {
        dates.add(it.timestamp.timestampToFormattedDate())
    }

    dates.forEach { messageDate ->
        delegates.add(DateDelegateItem(messageDate))
        val thisDayMessages = filter {
            it.timestamp.timestampToFormattedDate() == messageDate
        }

        var needDelegate: Boolean
        var currentTopic = ""
        thisDayMessages.forEach { message ->
            if (withTopics) {
                if (message.topicName == currentTopic)
                    needDelegate = false
                else {
                    needDelegate = true
                    currentTopic = message.topicName
                }

                if (needDelegate)
                    delegates.add(ChatTopicDelegateItem(currentTopic))
            }

            if (message.authorId == ownId)
                delegates.add(OutgoingMessageDelegateItem(id = message.id, value = message))
            else
                delegates.add(IncomingMessageDelegateItem(id = message.id, value = message))
        }
    }

    return delegates
}

fun List<Channel>.toDelegateItems(): List<DelegateItem> {
    val delegates = mutableListOf<DelegateItem>()

    forEach { channel ->
        delegates.add(ChannelDelegateItem(id = channel.id, value = channel))

        if (!channel.topics.isNullOrEmpty()) {
            channel.topics.forEach { topic ->
                delegates.add(TopicDelegateItem(topic))
            }
        }
    }

    return delegates
}

private fun Int.timestampToFormattedDate(): String {
    val targetFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val timestampInDays = timestampInDays()
    val date = Date(timestampInDays)

    return targetFormat.format(date)
}

private fun Int.timestampInDays(): Long =
    toLong() * IN_MILLIS - toLong() * IN_MILLIS % SECONDS_IN_DAY