package com.elmirov.course.chat.presentation

sealed interface ChatEffect {

    data object ShowError : ChatEffect
}