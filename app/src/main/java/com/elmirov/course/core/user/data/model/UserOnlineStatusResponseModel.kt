package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class UserOnlineStatusResponseModel(
    @SerializedName("presence") val aggregatedPresenceModel: AggregatedPresenceModel
)
