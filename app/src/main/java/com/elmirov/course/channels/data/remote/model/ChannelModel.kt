package com.elmirov.course.channels.data.remote.model

import com.google.gson.annotations.SerializedName

data class ChannelModel(
    @SerializedName("stream_id") val id: Int,
    val name: String,
)
