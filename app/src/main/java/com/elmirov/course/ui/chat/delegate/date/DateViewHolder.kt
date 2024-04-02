package com.elmirov.course.ui.chat.delegate.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.DateItemBinding

class DateViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
) {

    private val binding = DateItemBinding.bind(itemView)

    fun bind(date: String) {
        binding.date.text = date
    }
}