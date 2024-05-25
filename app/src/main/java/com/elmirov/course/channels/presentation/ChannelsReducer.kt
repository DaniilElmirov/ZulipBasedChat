package com.elmirov.course.channels.presentation

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.chat.domain.entity.ChatInfo
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
        const val EMPTY_NAME = ""
    }

    private var currentData: List<Channel> = emptyList()

    override fun Result.internal(event: ChannelsEvent.Internal): Any = when (event) {
        ChannelsEvent.Internal.LoadingError -> {
            state { copy(loading = false) }
            effects { +ChannelsEffect.ShowError }
        }

        is ChannelsEvent.Internal.ChannelsLoadingSuccess -> {
            if (event.data.isEmpty())
                state { copy(loading = true) }
            else {
                currentData = event.data
                state { copy(loading = false, content = event.data) }
            }
        }

        is ChannelsEvent.Internal.TopicsOpened -> {
            if (event.data.isEmpty())
                Unit //TODO нужен стейт загрузки и шиммер к нему на ui
            else {
                updateCurrentData(event.data)
                state { copy(loading = false, content = currentData) }
            }
        }

        is ChannelsEvent.Internal.TopicsClosed -> {
            val currentChannels = (state.content ?: emptyList()).toMutableList()
            val targetChannelIndex = currentChannels.indexOfFirst {
                it.id == event.channelId
            }

            currentChannels[targetChannelIndex] =
                currentChannels[targetChannelIndex].copy(expanded = false, topics = null)

            currentData = currentChannels
            state { copy(loading = false, content = currentChannels.toList()) }
        }

        is ChannelsEvent.Internal.SearchSuccess -> {
            if (event.query.isEmpty())
                state { copy(loading = false, content = currentData) }
            else {
                val searchedData = currentData.filter {
                    it.name.contains(other = event.query, ignoreCase = true)
                }

                state { copy(loading = false, content = searchedData) }
            }
        }

        is ChannelsEvent.Internal.CreateError -> {
            state { copy(loading = false) }
            effects { +ChannelsEffect.ShowError }
        }

        is ChannelsEvent.Internal.CreateSuccess -> {
            state { copy(loading = true) }
            if (event.subscribed)
                commands { +ChannelsCommand.LoadSubscribed }
            else
                commands { +ChannelsCommand.LoadAll }
        }
    }

    private fun updateCurrentData(topics: List<Topic>) {
        val currentChannels = currentData.toMutableList()

        val targetId = topics[0].topicChannelId
        val targetChannelIndex = currentChannels.indexOfFirst {
            it.id == targetId
        }

        currentChannels[targetChannelIndex] =
            currentChannels[targetChannelIndex].copy(expanded = true, topics = topics)

        currentData = currentChannels
    }

    override fun Result.ui(event: ChannelsEvent.Ui): Any = when (event) {

        ChannelsEvent.Ui.InitAll -> {
            commands { +ChannelsCommand.LoadCachedAll }
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadAll }
        }

        is ChannelsEvent.Ui.InitSubscribed -> {
            commands { +ChannelsCommand.LoadCachedSubscribed }
            state { copy(loading = true) }
            commands { +ChannelsCommand.LoadSubscribed }
        }

        is ChannelsEvent.Ui.OnCreateChannelClick -> {
            commands { +ChannelsCommand.Create(event.name, event.description, event.subscribed) }
        }

        is ChannelsEvent.Ui.OnChannelClick -> {
            val channel = state.content?.find { it.id == event.channelId }
            val channelName = channel?.name ?: EMPTY_NAME

            globalRouter.openChat(
                ChatInfo(
                    channelId = event.channelId,
                    channelName = channelName,
                    topicName = EMPTY_NAME
                )
            )
        }

        is ChannelsEvent.Ui.OnArrowClick -> {
            val targetChannel = state.content?.find {
                it.id == event.channelId
            }

            if (targetChannel?.expanded == true)
                commands { +ChannelsCommand.CloseTopics(event.channelId) }
            else {
                commands { +ChannelsCommand.OpenCachedTopics(event.channelId) }
                state { copy(loading = true) }
                commands { +ChannelsCommand.OpenTopics(event.channelId) }
            }
        }

        is ChannelsEvent.Ui.OnTopicClick -> {
            val channel = state.content?.find { it.id == event.channelId }
            val channelName = channel?.name ?: EMPTY_NAME

            globalRouter.openChat(
                ChatInfo(
                    channelId = event.channelId,
                    channelName = channelName,
                    topicName = event.topicName
                )
            )
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