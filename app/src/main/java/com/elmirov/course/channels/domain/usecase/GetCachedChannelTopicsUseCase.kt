package com.elmirov.course.channels.domain.usecase

import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.channels.domain.repository.ChannelTopicsRepository
import com.elmirov.course.core.result.domain.entity.Result
import javax.inject.Inject

class GetCachedChannelTopicsUseCase @Inject constructor(
    private val repository: ChannelTopicsRepository,
) : suspend (Int) -> Result<List<Topic>> by repository::getCachedByChannelId