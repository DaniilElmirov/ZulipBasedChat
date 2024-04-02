package com.elmirov.course.ui.chat.delegate.date

import com.elmirov.course.ui.adapter.delegate.DelegateItem

class DateDelegateItem(
    private val value: String
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        (other as DateDelegateItem).value == content()
}