package com.elmirov.course.channels.presentation

import com.elmirov.course.channels.domain.usecase.GetAllChannelsUseCase
import com.elmirov.course.channels.domain.usecase.GetCachedAllChannelsUseCase
import com.elmirov.course.channels.domain.usecase.GetCachedChannelTopicsUseCase
import com.elmirov.course.channels.domain.usecase.GetCachedSubscribedChannelsUseCase
import com.elmirov.course.channels.domain.usecase.GetChannelTopicsUseCase
import com.elmirov.course.channels.domain.usecase.GetSubscribedChannelsUseCase
import com.elmirov.course.core.result.domain.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.store.Actor
import javax.inject.Inject

class ChannelsActor @Inject constructor(
    private val getAllChannelsUseCase: GetAllChannelsUseCase,
    private val getCachedAllChannelsUseCase: GetCachedAllChannelsUseCase,
    private val getSubscribedChannelsUseCase: GetSubscribedChannelsUseCase,
    private val getCachedSubscribedChannelsUseCase: GetCachedSubscribedChannelsUseCase,
    private val getChannelTopicsUseCase: GetChannelTopicsUseCase,
    private val getCachedChannelTopicsUseCase: GetCachedChannelTopicsUseCase,
) : Actor<ChannelsCommand, ChannelsEvent>() {

    override fun execute(command: ChannelsCommand): Flow<ChannelsEvent> = flow {
        when (command) {
            ChannelsCommand.LoadAll -> {
                when (val result = getAllChannelsUseCase()) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.ChannelsLoadingSuccess(result.data))
                }
            }

            ChannelsCommand.LoadCachedAll -> {
                when (val result = getCachedAllChannelsUseCase()) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.ChannelsLoadingSuccess(result.data))
                }
            }

            ChannelsCommand.LoadSubscribed -> {
                when (val result = getSubscribedChannelsUseCase()) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.ChannelsLoadingSuccess(result.data))
                }
            }

            ChannelsCommand.LoadCachedSubscribed -> {
                when (val result = getCachedSubscribedChannelsUseCase()) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.ChannelsLoadingSuccess(result.data))
                }
            }

            is ChannelsCommand.OpenTopics -> {
                when (val result = getChannelTopicsUseCase(command.channelId)) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.TopicsOpened(result.data))
                }
            }

            is ChannelsCommand.OpenCachedTopics -> {
                when (val result = getCachedChannelTopicsUseCase(command.channelId)) {
                    is Result.Error -> emit(ChannelsEvent.Internal.LoadingError)
                    is Result.Success -> emit(ChannelsEvent.Internal.CachedTopicsOpened(result.data))
                }
            }

            is ChannelsCommand.CloseTopics -> {
                emit(ChannelsEvent.Internal.TopicsClosed(command.channelId))
            }

            is ChannelsCommand.Search -> {
                emit(ChannelsEvent.Internal.SearchSuccess(command.query))
            }
        }
    }
}