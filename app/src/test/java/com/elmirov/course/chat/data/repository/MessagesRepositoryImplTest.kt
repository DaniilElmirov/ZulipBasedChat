package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.local.dao.ChatDao
import com.elmirov.course.core.network.BaseResponseModel
import com.elmirov.course.chat.data.remote.network.MessagesApi
import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class MessagesRepositoryImplTest {

    private val api: MessagesApi = mock()
    private val dao: ChatDao = mock()
    private val dispatcherIo = StandardTestDispatcher()

    private val repository = MessagesRepositoryImpl(api, dao, dispatcherIo)

    private val channelName = "channelName"
    private val topicName = "topicName"
    private val text = "text"

    private val result = "result"
    private val baseResponse = BaseResponseModel(result)

    private val resultSuccess = Result.Success(result)
    private val resultError = Result.Error(ErrorType.UNKNOWN)

    @Test
    fun `send success EXPECT result success`() = runTest(dispatcherIo) {
        whenever(api.sendToChannelTopic(channelName, topicName, text)) doReturn baseResponse

        val expected = resultSuccess
        val actual = repository.sendToChannelTopic(channelName, topicName, text)

        assertEquals(expected, actual)
    }

    @Test
    fun `send error EXPECT result error`() = runTest(dispatcherIo) {
        whenever(api.sendToChannelTopic(channelName, topicName, text)) doThrow RuntimeException()

        val expected = resultError
        val actual = repository.sendToChannelTopic(channelName, topicName, text)

        assertEquals(expected, actual)
    }
}