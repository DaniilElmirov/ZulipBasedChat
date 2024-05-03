package com.elmirov.course.channels.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmirov.course.channels.data.local.model.ChannelDbModel

@Dao
interface ChannelsDao {

    private companion object {
        const val TRUE = 1
        const val FALSE = 0
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscribed(subscribedChannels: List<ChannelDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(allChannels: List<ChannelDbModel>)

    @Query("SELECT * FROM ChannelDbModel WHERE subscribed = $TRUE")
    suspend fun getSubscribed(): List<ChannelDbModel>

    @Query("SELECT * FROM ChannelDbModel WHERE subscribed = $FALSE")
    suspend fun getAll(): List<ChannelDbModel>
}