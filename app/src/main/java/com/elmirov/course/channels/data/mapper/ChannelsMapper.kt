package com.elmirov.course.channels.data.mapper

import com.elmirov.course.channels.data.local.model.ChannelDbModel
import com.elmirov.course.channels.data.remote.model.AllChannelsResponseModel
import com.elmirov.course.channels.data.remote.model.ChannelModel
import com.elmirov.course.channels.data.remote.model.SubscribedChannelsResponseModel
import com.elmirov.course.channels.domain.entity.Channel

fun AllChannelsResponseModel.toEntities(): List<Channel> = channels.map {
    it.toEntity()
}

fun SubscribedChannelsResponseModel.toEntities(): List<Channel> = channels.map {
    it.toEntity()
}

private fun ChannelModel.toEntity(): Channel =
    Channel(
        id = this.id,
        name = this.name,
    )

fun AllChannelsResponseModel.toDb(): List<ChannelDbModel> = channels.map { it.toDb(false) }

fun SubscribedChannelsResponseModel.toDb(): List<ChannelDbModel> = channels.map { it.toDb(true) }

fun List<ChannelDbModel>.toEntities(): List<Channel> = map { it.toEntity() }

private fun ChannelDbModel.toEntity(): Channel =
    Channel(
        id = channelId,
        name = name,
    )

private fun ChannelModel.toDb(subscribed: Boolean): ChannelDbModel =
    ChannelDbModel(
        channelId = id,
        name = name,
        subscribed = subscribed,
    )