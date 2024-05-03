package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.remote.network.TopicsApi
import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ChannelTopicsRepositoryImpl @Inject constructor(
    private val api: TopicsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ChannelTopicsRepository {

    override suspend fun getByChannelId(channelId: Int): Result<List<Topic>> =
        getResultWithHandleError(
            dispatcher = dispatcherIo,
            data = {
                api.getChannelTopics(channelId).toEntities(channelId)
            },
        )
}