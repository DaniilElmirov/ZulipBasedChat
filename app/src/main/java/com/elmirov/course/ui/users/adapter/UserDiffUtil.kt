package com.elmirov.course.ui.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elmirov.course.domain.User

class UserDiffUtil: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}