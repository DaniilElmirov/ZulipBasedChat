package com.elmirov.course.channels.domain.repository

import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.core.result.domain.entity.Result

interface ChannelTopicsRepository {

    suspend fun getByChannelId(channelId: Int): Result<List<Topic>>
}