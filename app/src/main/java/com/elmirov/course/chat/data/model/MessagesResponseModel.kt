package com.elmirov.course.chat.data.model

import com.google.gson.annotations.SerializedName

data class MessagesResponseModel(
    @SerializedName("messages") val messageModels: List<MessageModel>
)
