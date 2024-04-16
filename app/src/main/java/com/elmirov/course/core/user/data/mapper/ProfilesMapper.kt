package com.elmirov.course.core.user.data.mapper

import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.core.user.data.model.OtherProfileResponseModel
import com.elmirov.course.users.data.mapper.toEntity

fun OtherProfileResponseModel.toEntity(onlineStatus: User.OnlineStatus): User =
    userModel.toEntity(onlineStatus)