package com.elmirov.course.chat.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageDbModel(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    val channelName: String,
    val topicName: String,
    val timestamp: Int,
    val authorId: Int,
    val avatarUrl: String?,
    val authorName: String,
    val text: String,
) {
    companion object {
        const val COLUMN_ID = "id"
    }
}
