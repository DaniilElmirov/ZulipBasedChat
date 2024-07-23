package com.elmirov.course.users.data.repository

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.data.mapper.toEntity
import com.elmirov.course.core.user.data.network.OnlineStatusesApi
import com.elmirov.course.core.user.data.network.UsersApi
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.core.user.domain.repository.UsersRepository
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val onlineStatusesApi: OnlineStatusesApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : UsersRepository {

    override suspend fun get(): Result<List<User>> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            val userOnlineStatuses = onlineStatusesApi.getAll().onlineStatuses
            val users = usersApi.get().userModels.map { userModel ->
                val onlineStatus =
                    userOnlineStatuses[userModel.email]?.toEntity() ?: User.OnlineStatus.OFFLINE

                userModel.toEntity(onlineStatus)
            }
            users
        },
    )
}