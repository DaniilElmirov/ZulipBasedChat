package com.elmirov.course.chat.domain.usecase

import com.elmirov.course.chat.domain.repository.MessagesRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class EditMessageUseCase @Inject constructor(
    private val repository: MessagesRepository,
) : suspend (Int, String) -> Result<String> by repository::edit