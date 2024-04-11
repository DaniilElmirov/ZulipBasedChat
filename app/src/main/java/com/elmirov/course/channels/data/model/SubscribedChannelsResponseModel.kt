package com.elmirov.course.channels.data.model

import com.google.gson.annotations.SerializedName

data class SubscribedChannelsResponseModel(
    @SerializedName("msg") val message: String,
    val result: String,
    @SerializedName("subscriptions") val channels: List<ChannelModel>,
)
