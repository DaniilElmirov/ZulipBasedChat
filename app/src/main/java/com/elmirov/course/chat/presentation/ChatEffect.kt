package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.ChatInfo

sealed interface ChatEffect {

    data class ShowInfo(val chatInfo: ChatInfo) : ChatEffect

    data object ShowError : ChatEffect
}