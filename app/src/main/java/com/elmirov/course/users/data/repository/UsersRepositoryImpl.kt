package com.elmirov.course.users.data.repository

import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.core.entity.ErrorType
import com.elmirov.course.core.entity.Result
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.users.data.mapper.toEntities
import com.elmirov.course.users.data.network.UsersApi
import com.elmirov.course.users.domain.repository.UsersRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : UsersRepository {

    override suspend fun get(): Result<List<User>> =
        try {
            withContext(dispatcherIo) {
                Result.Success(api.get().toEntities())
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