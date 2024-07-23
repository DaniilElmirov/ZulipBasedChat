package com.elmirov.course.channels.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TopicDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_ID,
    val channelId: Int,
    val name: String,
) {
    companion object {
        private const val DEFAULT_ID = 0
    }
}