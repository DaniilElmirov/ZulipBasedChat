package com.elmirov.course.users.presentation

import com.elmirov.course.core.user.domain.entity.User

data class UsersState(
    val loading: Boolean = false,
    val content: List<User>? = null,
)