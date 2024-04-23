package com.elmirov.course.channels.presentation

sealed interface ChannelsCommand {
    data object LoadAll : ChannelsCommand

    data object LoadSubscribed : ChannelsCommand

    data class OpenTopics(val channelId: Int) : ChannelsCommand

    data class CloseTopics(val channelId: Int) : ChannelsCommand

    data class Search(val query: String): ChannelsCommand
}