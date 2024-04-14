package com.elmirov.course.chat.data.model

import com.google.gson.annotations.SerializedName

data class MessageModel(
    val id: Int,
    @SerializedName("sender_id") val authorId: Int,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("sender_full_name") val authorName: String,
    @SerializedName("content") val text: String,
)
