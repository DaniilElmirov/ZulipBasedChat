package com.elmirov.homework_1.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter : RecyclerView.Adapter<ContactsViewHolder>() {

    var contacts: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder =
        ContactsViewHolder(parent)

    override fun getItemCount(): Int =
        contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}