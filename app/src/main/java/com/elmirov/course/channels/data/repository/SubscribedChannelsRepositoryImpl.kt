package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.local.dao.ChannelsDao
import com.elmirov.course.channels.data.mapper.toDb
import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.remote.network.SubscribedChannelsApi
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SubscribedChannelsRepositoryImpl @Inject constructor(
    private val api: SubscribedChannelsApi,
    private val dao: ChannelsDao,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : SubscribedChannelsRepository {

    override suspend fun get(): Result<List<Channel>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val remoteData = api.get()
            dao.insertSubscribed(remoteData.toDb())

            dao.getSubscribed().toEntities()
        },
    )

    override suspend fun getCached(): Result<List<Channel>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            dao.getSubscribed().toEntities()
        },
    )
}