package com.elmirov.course.channels.presentation.subscribed

import com.elmirov.course.channels.domain.entity.Channel

sealed interface SubscribedChannelsState {

    data object Loading : SubscribedChannelsState

    data class Content(val data: List<Channel>) : SubscribedChannelsState
}