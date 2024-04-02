package com.elmirov.course.ui.channels.adapter.delegate.channel

import com.elmirov.course.domain.Channel
import com.elmirov.course.ui.chat.adapter.delegate.DelegateItem

class ChannelDelegateItem(
    val id: Int,
    private val value: Channel,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as ChannelDelegateItem).value == content()
}