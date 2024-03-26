package com.elmirov.course.util

import com.elmirov.course.domain.Message
import com.elmirov.course.ui.chat.adapter.date.DateDelegateItem
import com.elmirov.course.ui.chat.adapter.delegate.DelegateItem
import com.elmirov.course.ui.chat.adapter.incoming.IncomingMessageDelegateItem
import com.elmirov.course.ui.chat.adapter.outgoing.OutgoingMessageDelegateItem

fun List<Message>.toDelegateItems(
    ownId: Int,
): List<DelegateItem> {

    val delegates = mutableListOf<DelegateItem>()
    val dates = mutableSetOf<String>()

    val sortedMessages = sortedBy { it.date }

    sortedMessages.forEach {
        dates.add(it.date)
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