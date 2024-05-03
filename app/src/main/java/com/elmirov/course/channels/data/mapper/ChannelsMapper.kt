package com.elmirov.course.channels.data.mapper

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