package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.local.dao.ChannelsDao
import com.elmirov.course.channels.data.mapper.toDb
import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.remote.model.ChannelDataModel
import com.elmirov.course.channels.data.remote.network.SubscribedChannelsApi
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import com.google.gson.Gson
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
            dao.clearSubscribed()
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

    override suspend fun create(name: String, description: String): Result<String> =
        getResultWithHandleError(dispatcher = dispatcherIo) {
            val data = listOf(ChannelDataModel(name, description))
            val info = Gson().toJson(data)
            api.create(info).result
        }
}