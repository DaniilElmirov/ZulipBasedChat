package com.elmirov.course.presentation.channels.subscribed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.domain.Channel
import com.elmirov.course.domain.Topic
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubscribedChannelsViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    init {
        loadChannels()
    }

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
        MutableStateFlow<SubscribedChannelsState>(SubscribedChannelsState.Loading)
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

    fun openChat(topicName: String) {
        globalRouter.openChat(topicName)
    }

    fun search(query: String) {
        val currentState = _subscribedChannels.value as? SubscribedChannelsState.Content ?: return

        if (query.isEmpty()) {
            _subscribedChannels.value = SubscribedChannelsState.Content(testData.toList())
            return
        }

        val searchedData = mutableListOf<Channel>()

        currentState.data.forEach {
            if (it.name.contains(query)) {
                searchedData.add(it)
            }
        }

        _subscribedChannels.value = SubscribedChannelsState.Content(searchedData.toList())
    }

    private fun loadChannels() {
        viewModelScope.launch {
            delay(1000)
            _subscribedChannels.value = SubscribedChannelsState.Content(testData.toList())
        }
    }
}