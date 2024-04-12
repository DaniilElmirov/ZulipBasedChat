package com.elmirov.course.channels.domain.repository

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.core.result.domain.entity.Result

interface SubscribedChannelsRepository {

    suspend fun get(): Result<List<Channel>>
}