package com.elmirov.course.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elmirov.course.channels.data.local.dao.ChannelsDao
import com.elmirov.course.channels.data.local.dao.TopicsDao
import com.elmirov.course.channels.data.local.model.ChannelDbModel
import com.elmirov.course.channels.data.local.model.TopicDbModel
import com.elmirov.course.chat.data.local.dao.ChatDao
import com.elmirov.course.chat.data.local.model.MessageDbModel
import com.elmirov.course.chat.data.local.model.ReactionDbModel

@Database(
    entities = [
        MessageDbModel::class,
        ReactionDbModel::class,
        ChannelDbModel::class,
        TopicDbModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "AppDatabase"
    }

    abstract fun chatDao(): ChatDao

    abstract fun channelsDao(): ChannelsDao

    abstract fun topicsDao(): TopicsDao
}