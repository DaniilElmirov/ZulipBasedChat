package com.elmirov.course.channels.ui.delegate.channel

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.core.adapter.delegate.DelegateItem

class ChannelDelegateItem(
    val id: Int,
    private val value: Channel,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ChannelDelegateItem).value == content()
}