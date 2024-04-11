package com.elmirov.course.chat.ui.delegate.outgoing

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.core.adapter.delegate.DelegateItem

class OutgoingMessageDelegateItem(
    val id: Int,
    private val value: Message,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as OutgoingMessageDelegateItem).value == content()
}