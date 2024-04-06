package com.elmirov.course.presentation.channels.subscribed

import com.elmirov.course.domain.Channel

sealed interface SubscribedChannelsState {

    data object Loading : SubscribedChannelsState

    data class Content(val data: List<Channel>) : SubscribedChannelsState
}