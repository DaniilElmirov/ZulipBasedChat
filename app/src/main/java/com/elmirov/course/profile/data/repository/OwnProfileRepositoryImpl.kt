package com.elmirov.course.profile.data.repository

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.data.mapper.toEntity
import com.elmirov.course.core.user.data.network.OnlineStatusesApi
import com.elmirov.course.core.user.data.network.ProfileApi
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.core.user.domain.repository.OwnProfileRepository
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class OwnProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val onlineStatusesApi: OnlineStatusesApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : OwnProfileRepository {

    private companion object {
        const val OWN_ID = 709286 //TODO временно
    }

    override suspend fun get(): Result<User> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val onlineStatus = onlineStatusesApi.getById(OWN_ID).toEntity()
            profileApi.getOwn().toEntity(onlineStatus)
        },
    )
}