package com.elmirov.course.users.data.mapper

import com.elmirov.course.core.entity.User
import com.elmirov.course.users.data.model.UserModel
import com.elmirov.course.users.data.model.UsersResponseModel

fun UsersResponseModel.toEntities(): List<User> =
    userModels.map { it.toEntity() }

private fun UserModel.toEntity(): User =
    User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        email = email,
    )