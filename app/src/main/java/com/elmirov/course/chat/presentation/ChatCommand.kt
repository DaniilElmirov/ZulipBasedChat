package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatCommand {

    data object ShowInfo : ChatCommand //TODO как буд-то не совсем команда

    data object Load : ChatCommand

    data class Send(val text: String) : ChatCommand

    data class AddReaction(
        val messageId: Int,
        val reaction: Reaction,
        val selected: Boolean,
    ) : ChatCommand
}