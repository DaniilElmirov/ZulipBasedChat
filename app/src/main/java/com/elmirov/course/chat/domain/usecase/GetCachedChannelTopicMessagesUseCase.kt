package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class GetCachedChannelTopicMessagesUseCase @Inject constructor(
    private val repository: MessagesRepository,
) : suspend (String, String) -> Result<List<Message>> by repository::getCachedChannelTopicMessages