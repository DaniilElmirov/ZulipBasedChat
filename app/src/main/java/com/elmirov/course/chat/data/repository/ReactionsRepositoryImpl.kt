package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.network.ReactionsApi
import com.elmirov.course.chat.domain.repository.ReactionsRepository
import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.annotation.DispatcherIo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ReactionsRepositoryImpl @Inject constructor(
    private val api: ReactionsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ReactionsRepository {

    override suspend fun add(messageId: Int, emojiName: String, emojiCode: String): Result<String> =
        try {
            withContext(dispatcherIo) {
                Result.Success(api.add(messageId, emojiName, emojiCode).result)
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

    override suspend fun remove(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Result<String> =
        try {
            withContext(dispatcherIo) {
                Result.Success(api.remove(messageId, emojiName, emojiCode).result)
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
}