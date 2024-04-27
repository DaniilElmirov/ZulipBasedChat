package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.mapper.toEntities
import com.elmirov.course.chat.data.network.MessagesApi
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val api: MessagesApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : MessagesRepository {

    private companion object {
        const val STREAM_OPERATOR = "stream"
        const val TOPIC_OPERATOR = "topic"
    }

    override suspend fun getChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val narrow = getNarrowJson(channelName, topicName)
            api.getChannelTopicMessages(narrow).toEntities()
        },
    )

    override suspend fun sendToChannelTopic(
        channelName: String,
        topicName: String,
        text: String
    ): Result<String> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            api.sendToChannelTopic(channelName, topicName, text).result
        },
    )

    private fun getNarrowJson(channelName: String, topicName: String): String {
        val narrow = mutableListOf<Narrow>()

        if (channelName.isNotEmpty())
            narrow.add(Narrow(operator = STREAM_OPERATOR, operand = channelName))

        if (topicName.isNotEmpty())
            narrow.add(Narrow(operator = TOPIC_OPERATOR, operand = topicName))

        return Gson().toJson(narrow)
    }
}

private data class Narrow(
    val operator: String,
    val operand: String,
)