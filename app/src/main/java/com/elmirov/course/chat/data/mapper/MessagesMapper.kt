package com.elmirov.course.chat.data.mapper

import com.elmirov.course.chat.data.model.MessageModel
import com.elmirov.course.chat.data.model.MessagesResponseModel
import com.elmirov.course.chat.domain.entity.Message

fun MessagesResponseModel.toEntities(): List<Message> = messageModels.map { it.toEntity() }

private fun MessageModel.toEntity(): Message =
    Message(
        id = id,
        myMessage = myMessage,
        authorId = authorId,
        avatarUrl = avatarUrl,
        authorName = authorName,
        text = text,
    )