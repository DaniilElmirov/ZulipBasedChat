package com.elmirov.course.users.data.model

import com.google.gson.annotations.SerializedName

data class UserOnlineStatusResponseModel(
    @SerializedName("presence") val aggregatedPresenceModel: AggregatedPresenceModel
)
