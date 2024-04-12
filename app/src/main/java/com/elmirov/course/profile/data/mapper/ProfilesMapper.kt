package com.elmirov.course.profile.data.mapper

import com.elmirov.course.core.user.data.model.UserModel
import com.elmirov.course.core.user.domain.entity.User

fun UserModel.toEntity(): User =
    User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        email = email,
    )