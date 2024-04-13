package com.elmirov.course.profile.data.mapper

import com.elmirov.course.core.user.data.model.UserModel
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.profile.data.model.OtherProfileResponseModel

fun OtherProfileResponseModel.toEntity(): User = userModel.toEntity()

fun UserModel.toEntity(): User =
    User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        email = email,
    )