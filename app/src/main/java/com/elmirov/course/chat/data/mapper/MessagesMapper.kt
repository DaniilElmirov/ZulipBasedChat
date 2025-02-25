package com.elmirov.course.chat.data.mapper

import com.elmirov.course.chat.data.local.model.MessageDbModel
import com.elmirov.course.chat.data.local.model.MessageWithReactionsDbModel
import com.elmirov.course.chat.data.local.model.ReactionDbModel
import com.elmirov.course.chat.data.remote.model.MessageModel
import com.elmirov.course.chat.data.remote.model.MessagesResponseModel
import com.elmirov.course.chat.data.remote.model.ReactionModel
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.domain.entity.ReactionParams

fun MessagesResponseModel.toDb(
    channelName: String,
): List<MessageWithReactionsDbModel> =
    messageModels.map { it.toDb(channelName) }

fun List<MessageWithReactionsDbModel>.toEntities(): List<Message> = map { it.toEntity() }

private fun MessageModel.toDb(channelName: String): MessageWithReactionsDbModel =
    MessageWithReactionsDbModel(
        message = MessageDbModel(
            id = id,
            channelName = channelName,
            topicName = topic,
            timestamp = timestamp,
            authorId = authorId,
            avatarUrl = avatarUrl,
            authorName = authorName,
            text = text,
        ),
        reactions = reactionModels.map { it.toDb(id) }
    )

private fun MessageWithReactionsDbModel.toEntity(): Message =
    Message(
        id = message.id,
        timestamp = message.timestamp,
        topicName = message.topicName,
        authorId = message.authorId,
        avatarUrl = message.avatarUrl,
        authorName = message.authorName,
        text = message.text,
        reactions = reactions.toEntity()
    )

private fun ReactionModel.toDb(messageId: Int): ReactionDbModel =
    ReactionDbModel(
        messageId = messageId,
        userId = userId,
        emojiName = name,
        emojiCode = code,
        reactionType = reactionType
    )

private fun List<ReactionDbModel>.toEntity(): Map<Reaction, ReactionParams> {
    val result = mutableMapOf<Reaction, ReactionParams>()

    val ownId = 709286

    forEach { reactionDbModel ->
        val code =
            if (
                reactionDbModel.emojiCode.contains("-") ||
                reactionDbModel.reactionType != ReactionModel.REACTION_TYPE_UNICODE_EMOJI
            )
                ReactionModel.STUB_EMOJI_CODE
            else
                reactionDbModel.emojiCode

        val reaction = Reaction(
            emojiName = reactionDbModel.emojiName,
            emojiCode = code,
        )

        val selected =
            if (result[reaction]?.selected == true)
                true
            else
                ownId == reactionDbModel.userId

        val params = ReactionParams(
            count = count { it.emojiCode == reactionDbModel.emojiCode },
            selected = selected
        )

        result[reaction] = params
    }

    return result
}