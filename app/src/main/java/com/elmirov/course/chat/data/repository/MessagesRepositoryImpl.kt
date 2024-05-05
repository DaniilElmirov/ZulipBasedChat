package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.local.dao.ChatDao
import com.elmirov.course.chat.data.local.model.MessageWithReactionsDbModel
import com.elmirov.course.chat.data.mapper.toDb
import com.elmirov.course.chat.data.mapper.toEntities
import com.elmirov.course.chat.data.remote.network.MessagesApi
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
    private val dao: ChatDao,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : MessagesRepository {

    private companion object {
        const val STREAM_OPERATOR = "stream"
        const val TOPIC_OPERATOR = "topic"

        const val MAX_TABLE_SIZE = 50
    }

    override suspend fun getChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val narrow = getNarrowJson(channelName, topicName)
            val remoteData = api.getChannelTopicMessages(narrow)

            optimizeMessagesTable(remoteData.toDb(channelName, topicName))
            dao.insertMessages(remoteData.toDb(channelName, topicName))

            dao.getMessages(channelName, topicName).toEntities()
        },
    )

    override suspend fun getCachedChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            dao.getMessages(channelName, topicName).toEntities()
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

    override suspend fun loadNext(
        channelName: String,
        topicName: String,
        id: Int
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcherIo
    ) {
        val narrow = getNarrowJson(channelName, topicName)
        val remoteData = api.getNextMessages(
            narrow = narrow,
            anchor = id.toString(),
        )

        dao.insertMessages(remoteData.toDb(channelName, topicName))

        dao.getMessages(channelName, topicName).toEntities()
    }

    override suspend fun loadPrev(
        channelName: String,
        topicName: String,
        id: Int
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcherIo
    ) {
        val narrow = getNarrowJson(channelName, topicName)
        val remoteData = api.getPrevMessages(
            narrow = narrow,
            anchor = id.toString(),
        )

        dao.insertMessages(remoteData.toDb(channelName, topicName))

        dao.getMessages(channelName, topicName).toEntities()
    }

    private fun getNarrowJson(channelName: String, topicName: String): String {
        val narrow = mutableListOf<Narrow>()

        if (channelName.isNotEmpty())
            narrow.add(Narrow(operator = STREAM_OPERATOR, operand = channelName))

        if (topicName.isNotEmpty())
            narrow.add(Narrow(operator = TOPIC_OPERATOR, operand = topicName))

        return Gson().toJson(narrow)
    }

    private suspend fun optimizeMessagesTable(newMessages: List<MessageWithReactionsDbModel>) {
        val currentTableSize = dao.getTableSize()
        val expectedTableSize = currentTableSize + newMessages.size

        if (expectedTableSize <= MAX_TABLE_SIZE)
            return

        val channelName = newMessages.first().message.channelName
        val topicName = newMessages.first().message.topicName

        val messageCountInChat = dao.getMessagesCount(channelName, topicName)
        val messageCountInOtherChats = MAX_TABLE_SIZE - messageCountInChat

        val extraMessageCount = expectedTableSize - MAX_TABLE_SIZE
        val oldestCount = currentTableSize - messageCountInOtherChats

        if (extraMessageCount <= messageCountInOtherChats) {
            dao.deleteExtraMessagesNoInclude(channelName, topicName, extraMessageCount)
        } else {
            dao.deleteExtraMessagesNoInclude(channelName, topicName, messageCountInOtherChats)
            dao.deleteOldestInChat(oldestCount)
        }
    }
}

private data class Narrow(
    val operator: String,
    val operand: String,
)