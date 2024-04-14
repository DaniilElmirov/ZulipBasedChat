package com.elmirov.course.util

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegateItem
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegateItem
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.ui.delegate.date.DateDelegateItem
import com.elmirov.course.chat.ui.delegate.incoming.IncomingMessageDelegateItem
import com.elmirov.course.chat.ui.delegate.outgoing.OutgoingMessageDelegateItem
import com.elmirov.course.core.adapter.delegate.DelegateItem
import java.text.SimpleDateFormat
import java.util.Locale

fun List<Message>.toMessageDelegateItems(): List<DelegateItem> {

    val delegates = mutableListOf<DelegateItem>()
    val dates = mutableSetOf<String>()

    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    val sortedMessages = sortedBy { format.parse(it.date) }

    sortedMessages.forEach {
        dates.add(it.date)
    }

    dates.forEach { messageDate ->
        delegates.add(DateDelegateItem(messageDate))
        val thisDayMessages = filter {
            it.date == messageDate
        }

        thisDayMessages.forEach { message ->
            if (message.myMessage)
                delegates.add(OutgoingMessageDelegateItem(id = message.id, value = message))
            else
                delegates.add(IncomingMessageDelegateItem(id = message.id, value = message))
        }
    }

    return delegates
}

fun List<Channel>.toChannelDelegateItems(): List<DelegateItem> {
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