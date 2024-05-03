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
    @Query("SELECT * FROM MessageDbModel")
    suspend fun getMessages(): List<MessageWithReactionsDbModel>

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
}