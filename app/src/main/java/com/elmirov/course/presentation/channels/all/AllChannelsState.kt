package com.elmirov.course.presentation.channels.all

import com.elmirov.course.domain.Channel

sealed interface AllChannelsState {

    data class Content(val data: List<Channel>) : AllChannelsState
}