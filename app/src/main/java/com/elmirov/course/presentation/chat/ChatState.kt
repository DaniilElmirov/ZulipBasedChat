package com.elmirov.course.presentation.chat

import com.elmirov.course.domain.Message

sealed interface ChatState {

    data object Loading : ChatState

    data class Content(val data: List<Message>) : ChatState
}