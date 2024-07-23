package com.elmirov.course.chat.data.repository

import com.elmirov.course.chat.data.remote.network.ReactionsApi
import com.elmirov.course.chat.domain.repository.ReactionsRepository
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.di.application.annotation.DispatcherIo
import com.elmirov.course.util.getResultWithHandleError
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ReactionsRepositoryImpl @Inject constructor(
    private val api: ReactionsApi,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ReactionsRepository {

    override suspend fun add(messageId: Int, emojiName: String, emojiCode: String): Result<String> =
        getResultWithHandleError(
            dispatcher = dispatcherIo,
            data = {
                api.add(messageId, emojiName, emojiCode).result
            },
        )

    override suspend fun remove(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Result<String> = getResultWithHandleError(
        dispatcher = dispatcherIo,
        data = {
            api.remove(messageId, emojiName, emojiCode).result
        },
    )
}