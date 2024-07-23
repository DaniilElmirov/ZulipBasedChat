package com.elmirov.course.profile.presentation

import com.elmirov.course.core.user.domain.entity.User

data class ProfileState(
    val loading: Boolean = false,
    val content: User? = null,
)