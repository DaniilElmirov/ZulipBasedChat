package com.elmirov.course.chat.data.mapper

import com.elmirov.course.chat.data.remote.model.MessageModel
import com.elmirov.course.chat.data.remote.model.MessagesResponseModel
import com.elmirov.course.chat.data.remote.model.ReactionModel
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.entity.ReactionParams

fun MessagesResponseModel.toEntities(): List<Message> = messageModels.map { it.toEntity() }

private fun MessageModel.toEntity(): Message =
    Message(
        id = id,
        timestamp = timestamp,
        authorId = authorId,
        avatarUrl = avatarUrl,
        authorName = authorName,
        text = text,
        reactions = reactionModels.toEntity(),
    )

private fun List<ReactionModel>.toEntity(): Map<Reaction, ReactionParams> {
    val result = mutableMapOf<Reaction, ReactionParams>()

    val ownId = 709286 //TODO убрать в локальное хранилище

    forEach { reactionModel ->
        val code =
            if (//TODO переделать
                reactionModel.code.contains("-") ||
                reactionModel.reactionType != ReactionModel.REACTION_TYPE_UNICODE_EMOJI
            )
                ReactionModel.STUB_EMOJI_CODE
            else
                reactionModel.code

        val reaction = Reaction(
            emojiName = reactionModel.name,
            emojiCode = code,
        )

        val selected =
            if (result[reaction]?.selected == true)
                true
            else
                ownId == reactionModel.userId

        val params = ReactionParams(
            count = count { it.code == reactionModel.code },
            selected = selected
        )

        result[reaction] = params
    }

    return result
}