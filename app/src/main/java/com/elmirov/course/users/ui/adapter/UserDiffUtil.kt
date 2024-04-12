package com.elmirov.course.users.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.elmirov.course.core.user.domain.entity.User

class UserDiffUtil: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}