package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatEvent {

    sealed interface Ui : ChatEvent {
        data class Init(
            val channelId: Int,
            val channelName: String,
            val topicName: String,
        ) : Ui

        data class OnTopicClick(
            val channelId: Int,
            val channelName: String,
            val topicName: String,
        ) : Ui

        data class OnReactionClick(
            val messageId: Int,
            val reaction: Reaction,
            val selected: Boolean
        ) : Ui

        data class OnSendMessageClick(
            val text: String,
            val topicName: String,
        ) : Ui

        data object OnBackClick : Ui

        data object OnRefreshClick : Ui

        data object ScrollDown : Ui

        data object ScrollUp : Ui

        data class OnOutgoingMessageLongClick(val messageId: Int, val timestamp: Int) : Ui

        data class OnIncomingMessageLongClick(val messageId: Int, val timestamp: Int) : Ui

        data class OnDeleteClick(val messageId: Int) : Ui

        data class OnEditClick(val messageId: Int, val text: String) : Ui

        data class OnChangeTopicClick(val messageId: Int, val topicName: String) : Ui
    }

    sealed interface Internal : ChatEvent {

        data class ChatLoadingSuccess(val data: List<Message>) : Internal

        data object ChatLoadingError : Internal

        data object TopicsLoadingError : Internal

        data class TopicsLoadingSuccess(val data: List<String>) : Internal

        data object AddReactionError : Internal

        data object SendError : Internal

        data object DeleteSuccess : Internal

        data object EditSuccess : Internal

        data object ChangeTopicSuccess : Internal
    }
}