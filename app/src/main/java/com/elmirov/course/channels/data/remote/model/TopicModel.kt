package com.elmirov.course.channels.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopicModel(
    val name: String,
    @SerializedName("max_id") val lastMessageId: Int,
)
