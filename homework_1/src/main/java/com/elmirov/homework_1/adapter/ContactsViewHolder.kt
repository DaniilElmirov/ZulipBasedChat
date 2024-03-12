package com.elmirov.homework_1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.homework_1.R
import com.elmirov.homework_1.databinding.ContactItemBinding

class ContactsViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
) {

    private val binding = ContactItemBinding.bind(itemView)

    fun bind(contact: String) {
        binding.contact.text = contact
    }
}