package com.elmirov.course.ui.chat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.ui.chat.adapter.delegate.AdapterDelegate
import com.elmirov.course.ui.chat.adapter.delegate.DelegateAdapterItemCallback
import com.elmirov.course.ui.chat.adapter.delegate.DelegateItem

class MainAdapter :
    ListAdapter<DelegateItem, RecyclerView.ViewHolder>(DelegateAdapterItemCallback()) {

    private val delegates: MutableList<AdapterDelegate> = mutableListOf()

    fun addDelegate(delegate: AdapterDelegate) {
        delegates.add(delegate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        delegates[getItemViewType(position)].onBindViewHolder(
            holder,
            getItem(position),
            position
        )

    override fun getItemViewType(position: Int): Int = delegates.indexOfFirst {
        it.isOfViewType(currentList[position])
    }
}