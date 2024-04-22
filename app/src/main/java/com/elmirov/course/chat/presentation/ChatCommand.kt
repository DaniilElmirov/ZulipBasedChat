package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatCommand {
    data class Load(
        val channelName: String,
        val topicName: String
    ) : ChatCommand

    data class Send(
        val channelName: String,
        val topicName: String,
        val text: String
    ) : ChatCommand

    data class AddReaction(
        val channelName: String,
        val topicName: String,
        val messageId: Int,
        val reaction: Reaction,
        val selected: Boolean,
    ) : ChatCommand
}