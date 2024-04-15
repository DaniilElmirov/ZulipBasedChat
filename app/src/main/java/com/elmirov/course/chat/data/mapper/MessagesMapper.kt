package com.elmirov.course.chat.data.mapper

import com.elmirov.course.chat.data.model.MessageModel
import com.elmirov.course.chat.data.model.MessagesResponseModel
import com.elmirov.course.chat.data.model.ReactionModel
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.entity.ReactionParams

fun MessagesResponseModel.toEntities(): List<Message> = messageModels.map { it.toEntity() }

private fun MessageModel.toEntity(): Message =
    Message(
        id = id,
        authorId = authorId,
        avatarUrl = avatarUrl,
        authorName = authorName,
        text = text,
        reactions = reactionModels.toEntity(),
    )

private fun List<ReactionModel>.toEntity(): Map<Reaction, ReactionParams> =
    associate { reactionModel ->
        val ownId = 709286 //TODO убрать в локальное хранилище
        val code =
            if (reactionModel.code.contains("-") || //TODO переделать
                reactionModel.reactionType != ReactionModel.REACTION_TYPE_UNICODE_EMOJI
            )
                ReactionModel.STUB_EMOJI_CODE
            else
                reactionModel.code
        Reaction(
            emojiName = reactionModel.name,
            emojiCode = code,
        ) to ReactionParams(
            count = count { it.code == reactionModel.code },
            selected = ownId == reactionModel.userId
        )
    }