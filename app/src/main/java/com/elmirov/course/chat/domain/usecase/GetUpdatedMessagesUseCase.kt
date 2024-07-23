package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class GetUpdatedMessagesUseCase @Inject constructor(
    private val repository: MessagesRepository,
) {

    suspend operator fun invoke(
        channelName: String,
        topicName: String,
        id: Int,
    ): Result<List<Message>> = repository.getUpdated(channelName, topicName, id)
}