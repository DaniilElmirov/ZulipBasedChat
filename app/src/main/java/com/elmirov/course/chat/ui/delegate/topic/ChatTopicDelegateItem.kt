package com.elmirov.course.chat.ui.delegate.topic

import com.elmirov.course.core.adapter.delegate.DelegateItem

class ChatTopicDelegateItem(
    private val value: String
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ChatTopicDelegateItem).value == content()
}