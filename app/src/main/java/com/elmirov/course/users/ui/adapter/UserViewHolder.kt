package com.elmirov.course.users.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.elmirov.course.R
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.databinding.UserItemBinding

class UserViewHolder(
    parent: ViewGroup,
    private val onUserClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
) {

    private val binding = UserItemBinding.bind(itemView)

    fun bind(user: User) {
        binding.avatar.load(user.avatarUrl) {
            error(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
        binding.name.text = user.name
        binding.email.text = user.email

        binding.root.setOnClickListener {
            onUserClick(user.id)
        }
    }
}