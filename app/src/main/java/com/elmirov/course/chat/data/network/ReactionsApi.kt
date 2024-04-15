package com.elmirov.course.chat.data.network

import com.elmirov.course.chat.data.model.BaseResponseModel
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReactionsApi {

    @POST("messages/{message_id}/reactions")
    suspend fun add(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
    ): BaseResponseModel
}