package com.elmirov.course.channels.ui.delegate.topic

import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.core.adapter.delegate.DelegateItem

class TopicDelegateItem(
    private val value: Topic,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as TopicDelegateItem).value == content()
}