package com.elmirov.course.presentation.channels.all

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

class AllChannelsViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    init {
        loadChannels()
    }

    private val testData = mutableListOf(
        Channel(
            id = 0,
            name = "#general all"
        ),
        Channel(
            id = 1,
            name = "#computing all",
        ),
        Channel(
            id = 2,
            name = "#PR all"
        ),
    )

    private val topics = listOf(
        Topic(
            "первый all",
            12,
        ),
        Topic(
            "другой all",
            1,
        ),
        Topic(
            "last all",
            123,
        ),
    )

    private val _allChannels = MutableStateFlow<AllChannelsState>(AllChannelsState.Loading)
    val allChannels = _allChannels.asStateFlow()

    fun showTopics(channelId: Int) {
        val channelWithTopics = testData[channelId].copy(topics = topics)
        testData[channelId] = channelWithTopics
        _allChannels.value = AllChannelsState.Content(testData.toList())
    }

    fun closeTopics(channelId: Int) {
        val channelWithoutTopics = testData[channelId].copy(topics = null)
        testData[channelId] = channelWithoutTopics
        _allChannels.value = AllChannelsState.Content(testData.toList())
    }

    fun openChat(topicName: String) {
        globalRouter.openChat(topicName)
    }

    private fun loadChannels() {
        viewModelScope.launch {
            delay(1000)
            _allChannels.value = AllChannelsState.Content(testData.toList())
        }
    }
}