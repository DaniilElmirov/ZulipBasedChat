package com.elmirov.course.users.data.model

import com.google.gson.annotations.SerializedName

data class PresenceModel(
    @SerializedName("status") val onlineStatus: String,
    val timestamp: Int,
)
