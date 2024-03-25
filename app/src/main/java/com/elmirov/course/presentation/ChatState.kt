package com.elmirov.course.presentation

import com.elmirov.course.domain.Message

sealed interface ChatState {

    data class Content(val data: List<Message>) : ChatState
}