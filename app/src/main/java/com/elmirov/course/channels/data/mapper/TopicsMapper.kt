package com.elmirov.course.channels.data.mapper

import com.elmirov.course.channels.data.local.model.TopicDbModel
import com.elmirov.course.channels.data.remote.model.TopicModel
import com.elmirov.course.channels.data.remote.model.TopicsResponseModel
import com.elmirov.course.channels.domain.entity.Topic

fun TopicsResponseModel.toDb(topicChannelId: Int): List<TopicDbModel> =
    topicModels.map { it.toDb(topicChannelId) }

fun List<TopicDbModel>.toEntities(): List<Topic> = map { it.toEntity() }

private fun TopicDbModel.toEntity(): Topic =
    Topic(
        topicChannelId = channelId,
        name = name,
        messageCount = 0,
    )

private fun TopicModel.toDb(topicChannelId: Int): TopicDbModel =
    TopicDbModel(
        channelId = topicChannelId,
        name = name,
    )