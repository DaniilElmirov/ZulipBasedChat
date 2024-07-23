package com.elmirov.course.chat.presentation

sealed interface ChatEffect {

    data object ShowError : ChatEffect

    data class ShowMessageActionDialog(
        val messageId: Int,
        val messageText: String,
        val deletable: Boolean,
        val editable: Boolean,
        val transferable: Boolean,
    ) : ChatEffect
}