package com.elmirov.course.channels.presentation.all

import com.elmirov.course.channels.domain.entity.Channel

sealed interface AllChannelsState {

    data object Loading : AllChannelsState

    data class Content(val data: List<Channel>) : AllChannelsState

    data object Error : AllChannelsState
}