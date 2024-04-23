package com.elmirov.course.chat.data.network

import com.elmirov.course.chat.data.model.BaseResponseModel
import com.elmirov.course.chat.data.model.MessagesResponseModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagesApi {

    private companion object {
        const val MAX_NUM_BEFORE = 1000
        const val MAX_NUM_AFTER = 1000

        const val ANCHOR_FIRST_UNREAD = "first_unread"

        const val TYPE_STREAM = "stream"
    }

    @GET("messages")
    suspend fun getChannelTopicMessages(
        @Query("narrow") narrow: String,
        @Query("anchor") anchor: String = ANCHOR_FIRST_UNREAD,
        @Query("num_before") numBefore: Int = MAX_NUM_BEFORE,
        @Query("num_after") numAfter: Int = MAX_NUM_AFTER,
        @Query("apply_markdown") applyMarkdown: Boolean = false,
    ): MessagesResponseModel

    @POST("messages")
    suspend fun sendToChannelTopic(
        @Query("to") channelName: String,
        @Query("topic") topicName: String,
        @Query("content") text: String,
        @Query("type") type: String = TYPE_STREAM,
    ): BaseResponseModel
}