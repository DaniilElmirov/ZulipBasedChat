package com.elmirov.course.presentation.channels.subscribed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.domain.Channel
import com.elmirov.course.domain.Topic
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        MutableStateFlow<SubscribedChannelsState>(SubscribedChannelsState.Loading)
    val subscribedChannels = _subscribedChannels.asStateFlow()

    val searchQueryPublisher = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        loadChannels()
        listenSearchQuery()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            delay(1000)
            _subscribedChannels.value = SubscribedChannelsState.Content(testData.toList())
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun listenSearchQuery() {
        searchQueryPublisher
            .debounce(500)
            .mapLatest(::search)
            .flowOn(Dispatchers.Default)
            .onEach {
                _subscribedChannels.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun search(query: String): SubscribedChannelsState {

        if (query.isEmpty())
            return SubscribedChannelsState.Content(testData.toList())

        val searchedData = testData.filter {
            it.name.contains(other = query, ignoreCase = true)
        }

        return SubscribedChannelsState.Content(searchedData)
    }

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
}