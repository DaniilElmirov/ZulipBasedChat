package com.elmirov.course.util

import com.elmirov.course.domain.entity.Channel
import com.elmirov.course.domain.entity.Message
import com.elmirov.course.ui.channels.delegate.channel.ChannelDelegateItem
import com.elmirov.course.ui.channels.delegate.topic.TopicDelegateItem
import com.elmirov.course.ui.chat.delegate.date.DateDelegateItem
import com.elmirov.course.ui.adapter.delegate.DelegateItem
import com.elmirov.course.ui.chat.delegate.incoming.IncomingMessageDelegateItem
import com.elmirov.course.ui.chat.delegate.outgoing.OutgoingMessageDelegateItem

fun List<Message>.toDelegateItems(
    ownId: Int,
): List<DelegateItem> {

    val delegates = mutableListOf<DelegateItem>()
    val dates = mutableSetOf<String>()

    val sortedMessages = sortedBy { it.date }

    sortedMessages.forEach {
        dates.add(it.date) //TODO переделать на нормальное сравнение дат
    }

    dates.forEach { messageDate ->
        delegates.add(DateDelegateItem(messageDate))
        val thisDayMessages = filter {
            it.date == messageDate
        }

        thisDayMessages.forEach {
            if (it.userId == ownId)
                delegates.add(OutgoingMessageDelegateItem(id = it.id, value = it))
            else
                delegates.add(IncomingMessageDelegateItem(id = it.id, value = it))
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