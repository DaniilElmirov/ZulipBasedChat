package com.elmirov.course.users.data.mapper

import com.elmirov.course.core.user.data.model.UserModel
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.users.data.model.AggregatedPresenceModel
import com.elmirov.course.users.data.model.OnlineStatusModel
import java.time.LocalDateTime

private const val FIVE_MINUTES_IN_SECOND = 300

fun UserModel.toEntity(onlineStatus: User.OnlineStatus): User =
    User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        email = email,
        onlineStatus = onlineStatus,
    )

fun AggregatedPresenceModel.toEntity(): User.OnlineStatus =
    when (aggregated.onlineStatus) {
        OnlineStatusModel.ACTIVE.statusName -> User.OnlineStatus.ACTIVE
        OnlineStatusModel.IDLE.statusName -> User.OnlineStatus.IDLE
        else -> {
            val currentTimeInSecond = LocalDateTime.now().second

            if (currentTimeInSecond - aggregated.timestamp > FIVE_MINUTES_IN_SECOND)
                User.OnlineStatus.OFFLINE
            else
                User.OnlineStatus.OFFLINE
        }
    }