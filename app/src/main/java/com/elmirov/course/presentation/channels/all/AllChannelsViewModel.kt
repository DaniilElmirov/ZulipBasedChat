package com.elmirov.course.presentation.channels.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.di.annotation.DispatcherIo
import com.elmirov.course.domain.entity.Channel
import com.elmirov.course.domain.entity.Topic
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllChannelsViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
) : ViewModel() {

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

    val searchQueryPublisher = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        loadChannels()
        listenSearchQuery()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            try {
                withContext(dispatcherIo) {
                    delay(1000)
                    _allChannels.value = AllChannelsState.Content(testData.toList())
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (exception: Exception) { //TODO добавить стейт ошибки и его обработку
                _allChannels.value = AllChannelsState.Content(testData.toList())
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun listenSearchQuery() {
        searchQueryPublisher
            .debounce(500)
            .mapLatest(::search)
            .flowOn(Dispatchers.Default)
            .onEach {
                _allChannels.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun search(query: String): AllChannelsState {

        if (query.isEmpty())
            return AllChannelsState.Content(testData.toList())

        val searchedData = testData.filter {
            it.name.contains(other = query, ignoreCase = true)
        }

        return AllChannelsState.Content(searchedData)
    }

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
}