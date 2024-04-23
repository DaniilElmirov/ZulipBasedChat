package com.elmirov.course.channels.presentation

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer
import javax.inject.Inject

class ChannelsReducer @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ScreenDslReducer<
        ChannelsEvent,
        ChannelsEvent.Ui,
        ChannelsEvent.Internal,
        ChannelsState,
        ChannelsEffect,
        ChannelsCommand>(
    ChannelsEvent.Ui::class, ChannelsEvent.Internal::class
) {

    private companion object {
        const val EMPTY_CHANNEL_NAME = ""
    }

    private var loadedData: List<Channel> = emptyList()

    override fun Result.internal(event: ChannelsEvent.Internal): Any = when (event) {
        ChannelsEvent.Internal.LoadingError -> {
            state { copy(loading = false) }
            effects { +ChannelsEffect.ShowError }
        }

        is ChannelsEvent.Internal.ChannelsLoadingSuccess -> {
            loadedData = event.data
            state { copy(loading = false, content = event.data) }
        }

        is ChannelsEvent.Internal.TopicsOpened -> {
            val topics = event.data
            val currentChannels = (state.content ?: emptyList()).toMutableList()

            val targetId = event.data.getOrNull(0)?.topicChannelId ?: -1 //TODO переделать
            val targetChannelIndex = currentChannels.indexOfFirst {
                it.id == targetId
            }

            currentChannels[targetChannelIndex] =
                currentChannels[targetChannelIndex].copy(expanded = true, topics = topics)

            loadedData = currentChannels
            state { copy(loading = false, content = currentChannels.toList()) }
        }

        is ChannelsEvent.Internal.TopicsClosed -> {
            val currentChannels = (state.content ?: emptyList()).toMutableList()
            val targetChannelIndex = currentChannels.indexOfFirst {
                it.id == event.channelId
            }

            currentChannels[targetChannelIndex] =
                currentChannels[targetChannelIndex].copy(expanded = false, topics = null)

            loadedData = currentChannels
            state { copy(loading = false, content = currentChannels.toList()) }
        }

        is ChannelsEvent.Internal.SearchSuccess -> {
            if (event.query.isEmpty())
                state { copy(loading = false, content = loadedData) }
            else {
                val searchedData = loadedData.filter {
                    it.name.contains(other = event.query, ignoreCase = true)
                }

                state { copy(loading = false, content = searchedData) }
            }
        }
    }

    override fun Result.ui(event: ChannelsEvent.Ui): Any = when (event) {

        ChannelsEvent.Ui.InitAll -> {
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadAll }
        }

        is ChannelsEvent.Ui.InitSubscribed -> {
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadSubscribed }
        }

        is ChannelsEvent.Ui.OnChannelClick -> {
            val targetChannel = state.content?.find {
                it.id == event.channelId
            }

            if (targetChannel?.expanded == true)
                commands { +ChannelsCommand.CloseTopics(event.channelId) }
            else
                commands { +ChannelsCommand.OpenTopics(event.channelId) }
        }

        is ChannelsEvent.Ui.OnTopicClick -> {
            val channel = state.content?.find { it.id == event.channelId }
            val channelName = channel?.name ?: EMPTY_CHANNEL_NAME

            globalRouter.openChat(channelName, event.topicName)
        }

        is ChannelsEvent.Ui.Search -> commands { +ChannelsCommand.Search(event.query) }

        ChannelsEvent.Ui.OnRefreshAllClick -> {
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadAll }
        }

        ChannelsEvent.Ui.OnRefreshSubscribedClick -> {
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadSubscribed }
        }
    }
}