package com.elmirov.course.channels.presentation.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.usecase.GetAllChannelsUseCase
import com.elmirov.course.channels.domain.usecase.GetChannelTopicsUseCase
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

class AllChannelsViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val getAllChannelsUseCase: GetAllChannelsUseCase,
    private val getChannelTopicsUseCase: GetChannelTopicsUseCase,
) : ViewModel() {

    private companion object {
        const val EMPTY_CHANNEL_NAME = ""
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

    private val _allChannels = MutableStateFlow<AllChannelsState>(AllChannelsState.Loading)
    val allChannels = _allChannels.asStateFlow()

    val searchQueryPublisher = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        loadChannels()
        listenSearchQuery()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            when (val result = getAllChannelsUseCase()) {
                is Result.Error -> _allChannels.value = AllChannelsState.Error

                is Result.Success -> _allChannels.value = AllChannelsState.Content(result.data)
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

    //TODO переписать поиск

    private fun search(query: String): AllChannelsState {

        if (query.isEmpty())
            return AllChannelsState.Content(testData.toList())

        val searchedData = testData.filter {
            it.name.contains(other = query, ignoreCase = true)
        }

        return AllChannelsState.Content(searchedData)
    }

    //TODO совсем не уверен на счет адекватности твакого подхода
    //TODO возможно стоит работать с delegate item (тоже не очень)
    fun showTopics(channelId: Int) {
        val currentChannels =
            (_allChannels.value as? AllChannelsState.Content)?.data?.toMutableList() ?: return
        viewModelScope.launch {
            when (val result = getChannelTopicsUseCase(channelId)) {
                is Result.Error -> AllChannelsState.Error

                is Result.Success -> {
                    val targetChannelIndex = currentChannels.indexOfFirst {
                        it.id == channelId
                    }
                    currentChannels[targetChannelIndex] =
                        currentChannels[targetChannelIndex].copy(
                            expanded = true,
                            topics = result.data
                        )
                    _allChannels.value = AllChannelsState.Content(currentChannels.toList())
                }
            }
        }
    }

    fun closeTopics(channelId: Int) {
        val currentChannels =
            (_allChannels.value as? AllChannelsState.Content)?.data?.toMutableList() ?: return
        val targetChannelIndex = currentChannels.indexOfFirst {
            it.id == channelId
        }
        currentChannels[targetChannelIndex] =
            currentChannels[targetChannelIndex].copy(expanded = false, topics = null)
        _allChannels.value = AllChannelsState.Content(currentChannels.toList())
    }

    fun openChat(topicChannelId: Int, topicName: String) {
        val currentChannels =
            (_allChannels.value as? AllChannelsState.Content)?.data ?: return
        val topicChannelName =
            currentChannels.find { it.id == topicChannelId }?.name ?: EMPTY_CHANNEL_NAME

        globalRouter.openChat(topicChannelName, topicName)
    }
}