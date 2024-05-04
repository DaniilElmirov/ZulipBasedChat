package com.elmirov.course.channels.domain.usecase

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.AllChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class GetCachedAllChannelsUseCase @Inject constructor(
    private val repository: AllChannelsRepository,
) : suspend () -> Result<List<Channel>> by repository::getCached