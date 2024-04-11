package com.elmirov.course.channels.domain.usecase

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.core.entity.Result
import javax.inject.Inject

class GetAllChannelsUseCase @Inject constructor(
    private val repository: AllChannelsRepository,
) : suspend () -> Result<List<Channel>> by repository::get