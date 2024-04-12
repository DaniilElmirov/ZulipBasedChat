package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.network.SubscribedChannelsApi
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.core.entity.ErrorType
import com.elmirov.course.core.entity.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class SubscribedChannelsRepositoryImpl @Inject constructor(
    private val api: SubscribedChannelsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : SubscribedChannelsRepository {

    override suspend fun get(): Result<List<Channel>> =
        try {
            withContext(dispatcherIo) {
                Result.Success(api.get().toEntities())
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