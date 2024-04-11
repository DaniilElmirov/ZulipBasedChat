package com.elmirov.course.chat.ui.delegate.incoming

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.core.adapter.delegate.DelegateItem

class IncomingMessageDelegateItem(
    val id: Int,
    private val value: Message,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as IncomingMessageDelegateItem).value == content()
}