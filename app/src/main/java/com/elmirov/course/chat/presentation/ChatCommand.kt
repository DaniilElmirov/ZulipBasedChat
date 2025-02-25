package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.Reaction

sealed interface ChatCommand {
    data object Load : ChatCommand

    data object LoadCached : ChatCommand

    data object LoadTopics : ChatCommand

    data object LoadCachedTopics : ChatCommand

    data class LoadMore(val next: Boolean) : ChatCommand

    data class Send(val text: String, val topicName: String) : ChatCommand

    data class AddReaction(
        val messageId: Int,
        val reaction: Reaction,
        val selected: Boolean,
    ) : ChatCommand

    data class Delete(val messageId: Int) : ChatCommand

    data class Edit(val messageId: Int, val text: String) : ChatCommand

    data class ChangeTopic(val messageId: Int, val topicName: String) : ChatCommand
}