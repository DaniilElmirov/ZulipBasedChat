package com.elmirov.course.channels.data.model

import com.google.gson.annotations.SerializedName

data class AllChannelsResponseModel(
    @SerializedName("msg") val message: String,
    val result: String,
    @SerializedName("streams") val channels: List<ChannelModel>,
)
