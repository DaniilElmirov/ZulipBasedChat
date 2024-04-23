package com.elmirov.course.channels.presentation

import com.elmirov.course.channels.domain.entity.Channel

data class ChannelsState(
    val loading: Boolean = false,
    val content: List<Channel>? = null,
)
