package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.repository.ReactionsRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class AddReactionToMessageUseCase @Inject constructor(
    private val repository: ReactionsRepository,
) {

    suspend operator fun invoke(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Result<String> =
        repository.add(messageId, emojiName, emojiCode)
}