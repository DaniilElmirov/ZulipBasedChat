package com.elmirov.course.channels.domain.repository

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.core.result.domain.entity.Result

interface SubscribedChannelsRepository {

    suspend fun get(): Result<List<Channel>>

    suspend fun getCached(): Result<List<Channel>>

    suspend fun create(name: String, description: String): Result<String>
}