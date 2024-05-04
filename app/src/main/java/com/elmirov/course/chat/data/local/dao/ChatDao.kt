package com.elmirov.course.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.elmirov.course.chat.data.local.model.MessageDbModel
import com.elmirov.course.chat.data.local.model.MessageWithReactionsDbModel
import com.elmirov.course.chat.data.local.model.ReactionDbModel

@Dao
interface ChatDao {

    @Transaction
    @Query("SELECT * FROM MessageDbModel WHERE (channelName = :channelName AND topicName = :topicName)")
    suspend fun getMessages(
        channelName: String,
        topicName: String
    ): List<MessageWithReactionsDbModel>

    @Transaction
    suspend fun insertMessages(messages: List<MessageWithReactionsDbModel>) {
        messages.forEach { messageModel ->
            insertMessage(messageModel.message)
            messageModel.reactions.forEach {
                insertReaction(it)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReaction(reaction: ReactionDbModel)

    //TODO подумать над неймингом
    @Query("SELECT COUNT(*) FROM MessageDbModel")
    fun getTableSize(): Int

    @Query("SELECT COUNT(*) FROM MessageDbModel WHERE (channelName = :channelName AND topicName = :topicName)")
    suspend fun getMessagesCount(
        channelName: String,
        topicName: String
    ): Int

    @Query(
        "DELETE FROM MessageDbModel WHERE id IN " +
                "(SELECT id WHERE (channelName != :channelName AND topicName != :topicName) LIMIT :count)"
    )
    suspend fun deleteExtraMessagesNoInclude(
        channelName: String,
        topicName: String,
        count: Int
    )

    @Query(
        "DELETE FROM MessageDbModel WHERE timestamp IN " +
                "(SELECT timestamp FROM MessageDbModel ORDER BY timestamp ASC LIMIT :count)"
    )
    suspend fun deleteOldestInChat(
        count: Int
    )
}