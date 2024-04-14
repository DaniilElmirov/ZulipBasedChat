package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.mapper.toEntities
import com.elmirov.course.chat.data.network.MessagesApi
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.annotation.DispatcherIo
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
    ): Result<List<Message>> =
        try {
            withContext(dispatcherIo) {
                val narrow = getNarrowJson(channelName, topicName)
                Result.Success(api.getChannelTopicMessages(narrow).toEntities())
            }
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException, is SocketTimeoutException, is ConnectException -> {
                    Result.Error(errorType = ErrorType.CONNECTION)
                }

                else -> {
                    Result.Error(errorType = ErrorType.UNKNOWN)
                }
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
}

private data class Narrow(
    val operator: String,
    val operand: String,
)