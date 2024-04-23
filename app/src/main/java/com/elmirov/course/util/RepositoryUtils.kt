package com.elmirov.course.util

import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <D> getResultWithHandleError(
    dispatcher: CoroutineDispatcher,
    data: suspend () -> D
): Result<D> =
    try {
        withContext(dispatcher) {
            Result.Success(data())
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