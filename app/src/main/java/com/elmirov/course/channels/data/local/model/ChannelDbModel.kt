package com.elmirov.course.channels.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.elmirov.course.channels.data.local.model.ChannelDbModel.Companion.COLUMN_CHANNEL_ID
import com.elmirov.course.channels.data.local.model.ChannelDbModel.Companion.COLUMN_SUBSCRIBED

@Entity(indices = [Index(value = [COLUMN_CHANNEL_ID, COLUMN_SUBSCRIBED], unique = true)])
data class ChannelDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_ID,
    @ColumnInfo(name = COLUMN_CHANNEL_ID) val channelId: Int,
    val name: String,
    @ColumnInfo(name = COLUMN_SUBSCRIBED) val subscribed: Boolean,
) {
    companion object {
        private const val DEFAULT_ID = 0
        const val COLUMN_CHANNEL_ID = "channelId"
        const val COLUMN_SUBSCRIBED = "subscribed"
    }
}
