package com.elmirov.course.presentation.channels.subscribed

import androidx.lifecycle.ViewModel
import com.elmirov.course.domain.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubscribedChannelsViewModel : ViewModel() {

    private val testData = listOf(
        Channel(
            id = 1,
            name = "#general subscribed"
        ),
        Channel(
            id = 2,
            name = "#computing subscribed"
        ),
        Channel(
            id = 3,
            name = "#PR subscribed"
        ),
    )

    private val _subscribedChannels = MutableStateFlow(SubscribedChannelsState.Content(testData))
    val subscribedChannels = _subscribedChannels.asStateFlow()
}