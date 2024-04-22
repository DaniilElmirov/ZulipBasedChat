package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatEvent {

    sealed interface Ui : ChatEvent {
        data class Init(val channelName: String, val topicName: String) : Ui

        data class OnReactionClick(
            val channelName: String,
            val topicName: String,
            val messageId: Int,
            val reaction: Reaction,
            val selected: Boolean
        ) : Ui

        data class OnSendMessageClick(
            val channelName: String,
            val topicName: String,
            val text: String
        ) : Ui

        data object OnBackClick : Ui
    }

    sealed interface Internal : ChatEvent {
        data class ChatLoadingSuccess(val data: List<Message>) : Internal

        data object ChatLoadingError : Internal
    }
}