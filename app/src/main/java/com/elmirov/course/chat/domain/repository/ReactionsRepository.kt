package com.elmirov.course.chat.domain.repository

import com.elmirov.course.core.result.domain.entity.Result

interface ReactionsRepository {

    suspend fun add(
        messageId: Int,
        emojiName: String,
        emojiCode: String,
    ): Result<String>

    suspend fun remove(
        messageId: Int,
        emojiName: String,
        emojiCode: String,
    ): Result<String>
}