package com.elmirov.course.chat.domain.repository

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.core.result.domain.entity.Result

interface MessagesRepository {

    suspend fun getChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>>

    suspend fun sendToChannelTopic(
        channelName: String,
        topicName: String,
        text: String
    ): Result<String>
}