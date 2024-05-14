package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.remote.model.BaseResponseModel
import com.elmirov.course.chat.data.remote.network.ReactionsApi
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
class ReactionsRepositoryImplTest {

    private val api: ReactionsApi = mock()
    private val dispatcherIo = StandardTestDispatcher()
    private val repository = ReactionsRepositoryImpl(api, dispatcherIo)

    private val messageId = 1
    private val emojiName = "emojiName"
    private val emojiCode = "emojiCode"

    private val result = "result"
    private val baseResponse = BaseResponseModel(result)

    private val resultSuccess = Result.Success(result)
    private val resultError = Result.Error(ErrorType.UNKNOWN)

    @Test
    fun `add success EXPECT result success`() = runTest(dispatcherIo) {
        whenever(api.add(messageId, emojiName, emojiCode)) doReturn baseResponse

        val expected = resultSuccess
        val actual = repository.add(messageId, emojiName, emojiCode)

        assertEquals(expected, actual)
    }

    @Test
    fun `add error EXPECT result error`() = runTest(dispatcherIo) {
        whenever(api.add(messageId, emojiName, emojiCode)) doThrow RuntimeException()

        val expected = resultError
        val actual = repository.add(messageId, emojiName, emojiCode)

        assertEquals(expected, actual)
    }

    @Test
    fun `remove success EXPECT result success`() = runTest(dispatcherIo) {
        whenever(api.remove(messageId, emojiName, emojiCode)) doReturn baseResponse

        val expected = resultSuccess
        val actual = repository.remove(messageId, emojiName, emojiCode)

        assertEquals(expected, actual)
    }

    @Test
    fun `remove error EXPECT result error`() = runTest(dispatcherIo) {
        whenever(api.remove(messageId, emojiName, emojiCode)) doThrow RuntimeException()

        val expected = resultError
        val actual = repository.remove(messageId, emojiName, emojiCode)

        assertEquals(expected, actual)
    }
}