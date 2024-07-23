package com.elmirov.course.chat.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithReactionsDbModel(
    @Embedded val message: MessageDbModel,
    @Relation(
        parentColumn = MessageDbModel.COLUMN_ID,
        entityColumn = ReactionDbModel.COLUMN_MESSAGE_ID
    )
    val reactions: List<ReactionDbModel>,
)
