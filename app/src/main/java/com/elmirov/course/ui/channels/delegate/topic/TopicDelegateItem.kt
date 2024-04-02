package com.elmirov.course.ui.channels.delegate.topic

import com.elmirov.course.domain.Topic
import com.elmirov.course.ui.adapter.delegate.DelegateItem

class TopicDelegateItem(
    private val value: Topic,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TopicDelegateItem).value == content()
}