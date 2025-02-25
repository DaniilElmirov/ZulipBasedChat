package com.elmirov.course.channels.presentation

import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.domain.entity.Topic

sealed interface ChannelsEvent {

    sealed interface Ui : ChannelsEvent {
        data object InitAll : Ui

        data object InitSubscribed : Ui

        data class OnCreateChannelClick(
            val name: String,
            val description: String,
            val subscribed: Boolean,
        ) : Ui

        data class OnChannelClick(val channelId: Int) : Ui

        data class OnArrowClick(val channelId: Int) : Ui

        data class OnTopicClick(val channelId: Int, val topicName: String) : Ui

        data class Search(val query: String) : Ui

        data object OnRefreshAllClick : Ui

        data object OnRefreshSubscribedClick : Ui
    }

    sealed interface Internal : ChannelsEvent {
        data class ChannelsLoadingSuccess(val data: List<Channel>) : Internal

        data class TopicsOpened(val data: List<Topic>) : Internal

        data class TopicsClosed(val channelId: Int) : Internal

        data object LoadingError : Internal

        data class CreateSuccess(val subscribed: Boolean) : Internal

        data object CreateError : Internal

        data class SearchSuccess(val query: String) : Internal
    }
}