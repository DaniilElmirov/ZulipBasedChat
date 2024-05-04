package com.elmirov.course.channels.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmirov.course.channels.data.local.model.TopicDbModel

@Dao
interface TopicsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topics: List<TopicDbModel>)

    @Query("SELECT * FROM TopicDbModel WHERE channelId = :channelId")
    suspend fun get(channelId: Int): List<TopicDbModel>

    @Query("DELETE FROM TopicDbModel WHERE channelId = :channelId")
    suspend fun delete(channelId: Int)
}