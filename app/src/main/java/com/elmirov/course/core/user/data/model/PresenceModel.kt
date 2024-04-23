package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class PresenceModel(
    @SerializedName("status") val onlineStatus: String,
    val timestamp: Int,
)
