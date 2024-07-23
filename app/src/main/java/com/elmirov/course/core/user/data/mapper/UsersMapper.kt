package com.elmirov.course.core.user.data.mapper

import com.elmirov.course.core.user.data.model.OnlineStatusModel
import com.elmirov.course.core.user.data.model.PresenceInfoModel
import com.elmirov.course.core.user.data.model.UserModel
import com.elmirov.course.core.user.data.model.UserOnlineStatusResponseModel
import com.elmirov.course.core.user.domain.entity.User

private const val FIVE_MINUTES_IN_SECOND = 300

fun UserModel.toEntity(onlineStatus: User.OnlineStatus): User =
    User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        email = email,
        onlineStatus = onlineStatus,
    )

fun PresenceInfoModel.toEntity(): User.OnlineStatus = when {
    website.onlineStatus == OnlineStatusModel.ACTIVE.statusName ||
            aggregated.onlineStatus == OnlineStatusModel.ACTIVE.statusName -> User.OnlineStatus.ACTIVE

    website.onlineStatus == OnlineStatusModel.IDLE.statusName ||
            aggregated.onlineStatus == OnlineStatusModel.IDLE.statusName -> {

        val currentTimeInSecond = System.currentTimeMillis() / 1000
        val lastUpdate = maxOf(website.timestamp, aggregated.timestamp)

        if (currentTimeInSecond - lastUpdate > FIVE_MINUTES_IN_SECOND)
            User.OnlineStatus.OFFLINE
        else
            User.OnlineStatus.IDLE
    }

    else -> User.OnlineStatus.OFFLINE
}

fun UserOnlineStatusResponseModel.toEntity(): User.OnlineStatus =
    presence.toEntity()