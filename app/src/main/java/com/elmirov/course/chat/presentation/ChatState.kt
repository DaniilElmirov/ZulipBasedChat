package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Message

data class ChatState(
    val loading: Boolean = false,
    val content: List<Message>? = null
)