package com.elmirov.course.users.data.model

import com.google.gson.annotations.SerializedName

data class AllUsersOnlineStatusResponseModel(
    @SerializedName("presences") val onlineStatuses: Map<String, AggregatedPresenceModel>
)
