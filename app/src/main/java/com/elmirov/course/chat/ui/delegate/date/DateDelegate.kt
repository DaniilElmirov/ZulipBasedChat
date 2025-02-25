package com.elmirov.course.chat.ui.delegate.date

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.core.adapter.delegate.AdapterDelegate
import com.elmirov.course.core.adapter.delegate.DelegateItem

class DateDelegate: AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        DateViewHolder(parent)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as DateViewHolder).bind(item.content() as String)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DateDelegateItem
}