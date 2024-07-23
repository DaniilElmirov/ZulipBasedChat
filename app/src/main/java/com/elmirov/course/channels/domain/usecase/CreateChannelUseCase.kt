package com.elmirov.course.channels.domain.usecase

import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class CreateChannelUseCase @Inject constructor(
    private val repository: SubscribedChannelsRepository,
) {

    suspend operator fun invoke(name: String, description: String): Result<String> =
        repository.create(name, description)
}