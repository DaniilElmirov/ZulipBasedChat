package com.elmirov.course.users.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.R
import com.elmirov.course.databinding.UserItemBinding
import com.elmirov.course.users.domain.entity.User

class UserViewHolder(
    parent: ViewGroup,
    private val onUserClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
) {

    private val binding = UserItemBinding.bind(itemView)

    fun bind(user: User) {
        binding.name.text = user.name
        binding.mail.text = user.mail
        binding.root.setOnClickListener {
            onUserClick(user.id)
        }
    }
}