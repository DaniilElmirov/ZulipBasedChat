package com.elmirov.course.channels.data.repository

import com.elmirov.course.channels.data.mapper.toEntities
import com.elmirov.course.channels.data.network.AllChannelsApi
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AllChannelsRepositoryImpl @Inject constructor(
    private val api: AllChannelsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : AllChannelsRepository {

    override suspend fun get(): Result<List<Channel>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            api.get().toEntities()
        },
    )
}