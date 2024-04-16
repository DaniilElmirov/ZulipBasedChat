package com.elmirov.course.core.user.domain.entity

data class User(
    val id: Int,
    val avatarUrl: String?,
    val name: String,
    val email: String,
    val onlineStatus: OnlineStatus,
) {
    enum class OnlineStatus {
        ACTIVE,
        IDLE,
        OFFLINE,
    }
}