package com.elmirov.course.presentation.channels.subscribed

import androidx.lifecycle.ViewModel
import com.elmirov.course.domain.Channel
import com.elmirov.course.domain.Topic
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SubscribedChannelsViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    private val testData = mutableListOf(
        Channel(
            id = 0,
            name = "#general subscribed"
        ),
        Channel(
            id = 1,
            name = "#computing subscribed"
        ),
        Channel(
            id = 2,
            name = "#PR subscribed"
        ),
    )

    private val topics = listOf(
        Topic(
            "первый subscribed",
            12,
        ),
        Topic(
            "другой subscribed",
            1,
        ),
        Topic(
            "last subscribed",
            123,
        ),
    )

    private val _subscribedChannels =
        MutableStateFlow(SubscribedChannelsState.Content(testData.toList()))
    val subscribedChannels = _subscribedChannels.asStateFlow()

    fun showTopics(channelId: Int) {
        val channelWithTopics = testData[channelId].copy(topics = topics)
        testData[channelId] = channelWithTopics
        _subscribedChannels.value = SubscribedChannelsState.Content(testData.toList())
    }

    fun closeTopics(channelId: Int) {
        val channelWithoutTopics = testData[channelId].copy(topics = null)
        testData[channelId] = channelWithoutTopics
        _subscribedChannels.value = SubscribedChannelsState.Content(testData.toList())
    }

    fun openChat() {
        globalRouter.openChat()
    }
}