package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.local.dao.TopicsDao
import com.elmirov.course.channels.data.mapper.toDb
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
    private val dao: TopicsDao,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ChannelTopicsRepository {

    override suspend fun getByChannelId(channelId: Int): Result<List<Topic>> =
        getResultWithHandleError(
            dispatcher = dispatcherIo,
            data = {
                val remoteData = api.getChannelTopics(channelId)
                dao.clear(channelId)
                dao.insert(remoteData.toDb(channelId))

                dao.get(channelId).toEntities()
            },
        )

    override suspend fun getCachedByChannelId(channelId: Int): Result<List<Topic>> =
        getResultWithHandleError(
            dispatcher = dispatcherIo,
            data = {
                dao.get(channelId).toEntities()
            },
        )
}