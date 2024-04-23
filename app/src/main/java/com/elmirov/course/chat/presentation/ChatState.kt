package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Message

sealed interface ChatState {

    data object Loading : ChatState

    data class Content(val data: List<Message>) : ChatState

    data object Error : ChatState
}