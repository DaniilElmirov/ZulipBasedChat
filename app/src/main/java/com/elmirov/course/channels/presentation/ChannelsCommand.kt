package com.elmirov.course.channels.presentation

sealed interface ChannelsCommand {
    data object LoadAll : ChannelsCommand

    data object LoadCachedAll : ChannelsCommand

    data object LoadSubscribed : ChannelsCommand

    data object LoadCachedSubscribed : ChannelsCommand

    data class Create(
        val name: String,
        val description: String,
        val subscribed: Boolean
    ) : ChannelsCommand

    data class OpenTopics(val channelId: Int) : ChannelsCommand

    data class OpenCachedTopics(val channelId: Int) : ChannelsCommand

    data class CloseTopics(val channelId: Int) : ChannelsCommand

    data class Search(val query: String) : ChannelsCommand
}