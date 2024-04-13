package com.elmirov.course.channels.data.model

import com.google.gson.annotations.SerializedName

data class TopicsResponseModel(
    @SerializedName("topics") val topicModels: List<TopicModel>,
)
