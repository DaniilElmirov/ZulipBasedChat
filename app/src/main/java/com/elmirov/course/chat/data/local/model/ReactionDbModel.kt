package com.elmirov.course.chat.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.elmirov.course.chat.data.local.model.ReactionDbModel.Companion.COLUMN_EMOJI_CODE
import com.elmirov.course.chat.data.local.model.ReactionDbModel.Companion.COLUMN_MESSAGE_ID
import com.elmirov.course.chat.data.local.model.ReactionDbModel.Companion.COLUMN_USER_ID

@Entity(
    foreignKeys = [ForeignKey(
        entity = MessageDbModel::class,
        parentColumns = [MessageDbModel.COLUMN_ID],
        childColumns = [COLUMN_MESSAGE_ID],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(
        value = [COLUMN_MESSAGE_ID, COLUMN_USER_ID, COLUMN_EMOJI_CODE],
        unique = true
    )]
)
data class ReactionDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_ID,
    @ColumnInfo(name = COLUMN_MESSAGE_ID) val messageId: Int,
    @ColumnInfo(name = COLUMN_USER_ID) val userId: Int,
    val emojiName: String,
    @ColumnInfo(name = COLUMN_EMOJI_CODE) val emojiCode: String,
    val reactionType: String,
) {
    companion object {
        private const val DEFAULT_ID = 0
        const val COLUMN_MESSAGE_ID = "messageId"
        const val COLUMN_USER_ID = "userId"
        const val COLUMN_EMOJI_CODE = "emojiCode"
    }
}
