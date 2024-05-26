package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.local.dao.ChatDao
import com.elmirov.course.chat.data.mapper.toDb
import com.elmirov.course.chat.data.mapper.toEntities
import com.elmirov.course.chat.data.remote.network.MessagesApi
import com.elmirov.course.chat.data.remote.network.MessagesApi.Companion.UPDATED_NUM_AFTER
import com.elmirov.course.chat.data.remote.network.MessagesApi.Companion.UPDATED_NUM_BEFORE
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

    override suspend fun getLasts(channelName: String, topicName: String): Result<List<Message>> =
        getResultWithHandleError(dispatcher = dispatcherIo) {
            val narrow = getNarrowJson(channelName, topicName)
            val remoteData = api.getChannelTopicMessages(
                narrow = narrow,
                anchor = MessagesApi.ANCHOR_NEWEST,
                numBefore = 1,
                numAfter = MessagesApi.NEXT_NUM_AFTER,
            )

            optimizeMessagesTable(channelName, topicName, remoteData.messageModels.size, true)
            dao.insertMessages(remoteData.toDb(channelName))

            dao.getMessages(channelName, topicName).toEntities()
        }

    override suspend fun getUpdated(
        channelName: String,
        topicName: String,
        id: Int,
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
    ) {
        val narrow = getNarrowJson(channelName, topicName)
        val remoteData = api.getChannelTopicMessages(
            narrow = narrow,
            anchor = id.toString(),
            numBefore = UPDATED_NUM_BEFORE,
            numAfter = UPDATED_NUM_AFTER,
        )

        if (topicName.isEmpty()) {
            optimizeMessagesTable(channelName, remoteData.messageModels.size, true)
            dao.insertMessages(remoteData.toDb(channelName))

            dao.getMessages(channelName).toEntities()
        } else {
            optimizeMessagesTable(channelName, topicName, remoteData.messageModels.size, true)
            dao.insertMessages(remoteData.toDb(channelName))

            dao.getMessages(channelName, topicName).toEntities()
        }
    }

    override suspend fun getChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val narrow = getNarrowJson(channelName, topicName)
            val remoteData = api.getChannelTopicMessages(narrow)

            if (topicName.isEmpty()) {
                dao.clear(channelName)
                optimizeMessagesTable(channelName, remoteData.messageModels.size, true)
                dao.insertMessages(remoteData.toDb(channelName))
                dao.getMessages(channelName).toEntities()
            } else {
                dao.clear(channelName, topicName)
                optimizeMessagesTable(channelName, topicName, remoteData.messageModels.size, true)
                dao.insertMessages(remoteData.toDb(channelName))
                dao.getMessages(channelName, topicName).toEntities()
            }
        },
    )

    override suspend fun getCachedChannelTopicMessages(
        channelName: String,
        topicName: String
    ): Result<List<Message>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            if (topicName.isEmpty())
                dao.getMessages(channelName).toEntities()
            else
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

        if (topicName.isEmpty()) {
            optimizeMessagesTable(channelName, remoteData.messageModels.size, true)
            dao.insertMessages(remoteData.toDb(channelName))
            dao.getMessages(channelName).toEntities()
        } else {
            optimizeMessagesTable(channelName, topicName, remoteData.messageModels.size, true)
            dao.insertMessages(remoteData.toDb(channelName))
            dao.getMessages(channelName, topicName).toEntities()
        }
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

        if (topicName.isEmpty()) {
            optimizeMessagesTable(channelName, remoteData.messageModels.size, false)
            dao.insertMessages(remoteData.toDb(channelName))
            dao.getMessages(channelName).toEntities()
        } else {
            optimizeMessagesTable(channelName, topicName, remoteData.messageModels.size, false)
            dao.insertMessages(remoteData.toDb(channelName))
            dao.getMessages(channelName, topicName).toEntities()
        }
    }

    private fun getNarrowJson(channelName: String, topicName: String): String {
        val narrow = mutableListOf<Narrow>()

        if (channelName.isNotEmpty())
            narrow.add(Narrow(operator = STREAM_OPERATOR, operand = channelName))

        if (topicName.isNotEmpty())
            narrow.add(Narrow(operator = TOPIC_OPERATOR, operand = topicName))

        return Gson().toJson(narrow)
    }

    private suspend fun optimizeMessagesTable(
        channelName: String,
        topicName: String,
        size: Int,
        oldest: Boolean
    ) {
        val currentTableSize = dao.getTableSize()
        val expectedSize = currentTableSize + size

        if (expectedSize <= MAX_TABLE_SIZE)
            return

        var extraCount = expectedSize - MAX_TABLE_SIZE
        val inOtherCount = dao.getOtherMessagesCount(channelName, topicName)

        if (extraCount <= inOtherCount) {
            dao.deleteExtraMessagesNoInclude(channelName, topicName, extraCount)
            return
        } else {
            dao.deleteExtraMessagesNoInclude(channelName, topicName, inOtherCount)
            extraCount -= inOtherCount
        }

        if (oldest)
            dao.deleteOldestInChat(channelName, topicName, extraCount)
        else
            dao.deleteNewestInChat(channelName, topicName, extraCount)
    }

    private suspend fun optimizeMessagesTable(
        channelName: String,
        size: Int,
        oldest: Boolean
    ) {
        val currentTableSize = dao.getTableSize()
        val expectedSize = currentTableSize + size

        if (expectedSize <= MAX_TABLE_SIZE)
            return

        var extraCount = expectedSize - MAX_TABLE_SIZE
        val inOtherCount = dao.getOtherMessagesCount(channelName)

        if (extraCount <= inOtherCount) {
            dao.deleteExtraMessagesNoInclude(channelName, extraCount)
            return
        } else {
            dao.deleteExtraMessagesNoInclude(channelName, inOtherCount)
            extraCount -= inOtherCount
        }

        if (oldest)
            dao.deleteOldestInChat(channelName, extraCount)
        else
            dao.deleteNewestInChat(channelName, extraCount)
    }
}

private data class Narrow(
    val operator: String,
    val operand: String,
)