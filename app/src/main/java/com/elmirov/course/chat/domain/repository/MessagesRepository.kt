package com.elmirov.course.chat.domain.repository

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.core.result.domain.entity.Result

interface MessagesRepository {

    suspend fun getUpdated(
        channelName: String,
        topicName: String,
        id: Int,
    ): Result<List<Message>>

    suspend fun getLasts(
        channelName: String,
        topicName: String,
    ): Result<List<Message>>

    suspend fun getChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>>

    suspend fun getCachedChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>>

    suspend fun sendToChannelTopic(
        channelName: String,
        topicName: String,
        text: String
    ): Result<String>

    suspend fun loadNext(
        channelName: String,
        topicName: String,
        id: Int,
    ): Result<List<Message>>

    suspend fun loadPrev(
        channelName: String,
        topicName: String,
        id: Int,
    ): Result<List<Message>>

    suspend fun delete(messageId: Int): Result<String>

    suspend fun edit(messageId: Int, text: String): Result<String>

    suspend fun changeTopic(messageId: Int, topicName: String): Result<String>
}