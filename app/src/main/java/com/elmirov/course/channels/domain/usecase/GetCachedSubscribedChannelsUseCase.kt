package com.elmirov.course.channels.domain.usecase

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.repository.SubscribedChannelsRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class GetCachedSubscribedChannelsUseCase @Inject constructor(
    private val repository: SubscribedChannelsRepository,
) : suspend () -> Result<List<Channel>> by repository::getCached