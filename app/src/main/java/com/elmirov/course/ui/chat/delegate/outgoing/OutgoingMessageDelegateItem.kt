package com.elmirov.course.ui.chat.delegate.outgoing

import com.elmirov.course.domain.entity.Message
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class OutgoingMessageDelegateItem(
    val id: Int,
    private val value: Message,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as OutgoingMessageDelegateItem).value == content()
}