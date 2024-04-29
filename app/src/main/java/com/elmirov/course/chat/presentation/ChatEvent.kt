package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatEvent {

    sealed interface Ui : ChatEvent {
        data object Init : Ui

        data class OnReactionClick(
            val messageId: Int,
            val reaction: Reaction,
            val selected: Boolean
        ) : Ui

        data class OnSendMessageClick(
            val text: String
        ) : Ui

        data object OnBackClick : Ui
    }

    sealed interface Internal : ChatEvent {
        data class ShowInfo(val chatInfo: ChatInfo) : Internal

        data class ChatLoadingSuccess(val data: List<Message>) : Internal

        data object ChatLoadingError : Internal
    }
}