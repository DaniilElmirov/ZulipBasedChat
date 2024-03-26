package com.elmirov.course.util

import com.elmirov.course.domain.Message
import com.elmirov.course.ui.chat.adapter.incoming.IncomingMessageDelegateItem
import com.elmirov.course.ui.chat.adapter.outgoing.OutgoingMessageDelegateItem
import com.elmirov.course.ui.chat.adapter.delegate.DelegateItem

fun List<Message>.toDelegateItems(
    ownId: Int,
): List<DelegateItem> = map {
    if (it.userId == ownId)
        OutgoingMessageDelegateItem(id = it.id, value = it)
    else
        IncomingMessageDelegateItem(id = it.id, value = it)
}