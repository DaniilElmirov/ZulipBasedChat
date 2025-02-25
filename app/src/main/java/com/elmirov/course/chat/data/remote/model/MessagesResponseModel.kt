package com.elmirov.course.chat.data.remote.model

import com.google.gson.annotations.SerializedName

data class MessagesResponseModel(
    @SerializedName("messages") val messageModels: List<MessageModel>
)
