package com.elmirov.course.chat.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = MessageDbModel::class,
        parentColumns = [MessageDbModel.COLUMN_ID],
        childColumns = [ReactionDbModel.COLUMN_MESSAGE_ID],
        onDelete = ForeignKey.CASCADE
    )],
)
data class ReactionDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_ID,
    @ColumnInfo(name = COLUMN_MESSAGE_ID) val messageId: Int,
    val userId: Int,
    val emojiName: String,
    val emojiCode: String,
    val reactionType: String,
) {
    companion object {
        private const val DEFAULT_ID = 0
        const val COLUMN_MESSAGE_ID = "messageId"
    }
}
