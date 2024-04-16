package com.elmirov.course.profile.data.repository

import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.profile.data.mapper.toEntity
import com.elmirov.course.profile.data.network.ProfileApi
import com.elmirov.course.profile.domain.repository.OtherProfileRepository
import com.elmirov.course.users.data.mapper.toEntity
import com.elmirov.course.users.data.network.OnlineStatusesApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class OtherProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val onlineStatusesApi: OnlineStatusesApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : OtherProfileRepository {

    override suspend fun getById(id: Int): Result<User> =
        try {
            withContext(dispatcherIo) {
                val onlineStatus = onlineStatusesApi.getById(id).toEntity()
                Result.Success(profileApi.getOtherById(id).toEntity(onlineStatus))
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