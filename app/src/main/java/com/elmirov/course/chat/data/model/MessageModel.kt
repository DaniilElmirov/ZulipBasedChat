package com.elmirov.course.chat.data.model

import com.google.gson.annotations.SerializedName

data class MessageModel(
    val id: Int,
    @SerializedName("sender_id") val authorId: Int,
    @SerializedName("is_me_message") val myMessage: Boolean,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("sender_full_name") val authorName: String,
    @SerializedName("content") val text: String,
)
