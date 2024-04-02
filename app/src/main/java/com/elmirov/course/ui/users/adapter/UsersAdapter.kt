package com.elmirov.course.ui.users.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.elmirov.course.domain.User

class UsersAdapter(
    private val onUserClick: (Int) -> Unit,
) : ListAdapter<User, UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(parent, onUserClick)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}