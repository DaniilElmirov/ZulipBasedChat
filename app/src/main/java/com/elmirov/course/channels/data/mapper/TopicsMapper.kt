package com.elmirov.course.channels.data.mapper

import com.elmirov.course.channels.data.model.TopicModel
import com.elmirov.course.channels.data.model.TopicsResponseModel
import com.elmirov.course.channels.domain.entity.Topic

fun TopicsResponseModel.toEntities(topicChannelId: Int): List<Topic> =
    topicModels.map { it.toEntity(topicChannelId) }

private fun TopicModel.toEntity(topicChannelId: Int): Topic =
    Topic(
        topicChannelId = topicChannelId,
        name = name,
        messageCount = lastMessageId, //TODO временно, посмотреть api сообщений
    )