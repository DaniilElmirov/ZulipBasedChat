package com.elmirov.course.users.data.repository

import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.core.user.data.mapper.toEntity
import com.elmirov.course.core.user.data.network.OnlineStatusesApi
import com.elmirov.course.core.user.data.network.UsersApi
import com.elmirov.course.core.user.domain.repository.UsersRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val onlineStatusesApi: OnlineStatusesApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : UsersRepository {

    override suspend fun get(): Result<List<User>> =
        try {
            withContext(dispatcherIo) {
                val userOnlineStatuses = onlineStatusesApi.getAll().onlineStatuses
                val users = usersApi.get().userModels.map { userModel ->
                    val onlineStatus =
                        userOnlineStatuses[userModel.email]?.toEntity() ?: User.OnlineStatus.OFFLINE

                    userModel.toEntity(onlineStatus)
                }
                Result.Success(users)
            }
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException, is SocketTimeoutException, is ConnectException -> {
                    Result.Error(errorType = ErrorType.CONNECTION)
                }

                else -> Result.Error(errorType = ErrorType.UNKNOWN)
            }
        }
}