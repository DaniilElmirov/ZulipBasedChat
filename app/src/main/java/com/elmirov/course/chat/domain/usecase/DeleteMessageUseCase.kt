package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class DeleteMessageUseCase @Inject constructor(
    private val repository: MessagesRepository,
) : suspend (Int) -> Result<String> by repository::delete