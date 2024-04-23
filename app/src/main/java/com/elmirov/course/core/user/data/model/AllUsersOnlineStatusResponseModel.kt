package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class AllUsersOnlineStatusResponseModel(
    @SerializedName("presences") val onlineStatuses: Map<String, PresenceInfoModel>
)
