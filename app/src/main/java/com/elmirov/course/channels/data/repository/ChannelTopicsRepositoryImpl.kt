package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.network.TopicsApi
import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
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

class ChannelTopicsRepositoryImpl @Inject constructor(
    private val api: TopicsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ChannelTopicsRepository {

    override suspend fun getByChannelId(channelId: Int): Result<List<Topic>> =
        try {
            withContext(dispatcherIo) {
                Result.Success(api.getChannelTopics(channelId).toEntities(channelId))
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