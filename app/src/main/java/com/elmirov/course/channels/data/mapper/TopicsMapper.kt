package com.elmirov.course.channels.data.mapper

import com.elmirov.course.channels.data.model.TopicModel
import com.elmirov.course.channels.data.model.TopicsResponseModel
import com.elmirov.course.channels.domain.entity.Topic

fun TopicsResponseModel.toEntities(): List<Topic> = topicModels.map { it.toEntity() }

private fun TopicModel.toEntity(): Topic =
    Topic(
        name = name,
        messageCount = lastMessageId, //TODO временно, посмотреть api сообщений
    )