package com.elmirov.course.ui.chat.delegate.incoming

import com.elmirov.course.domain.Message
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class IncomingMessageDelegateItem(
    val id: Int,
    private val value: Message,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as IncomingMessageDelegateItem).value == content()
}