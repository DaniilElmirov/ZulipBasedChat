package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class AddReactionToMessageUseCase @Inject constructor(
    private val repository: MessagesRepository,
) {

    suspend operator fun invoke(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Result<String> =
        repository.addReaction(messageId, emojiName, emojiCode)
}