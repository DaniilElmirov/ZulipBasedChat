package com.elmirov.course.chat.data.remote.network

import com.elmirov.course.chat.data.remote.model.BaseResponseModel
import com.elmirov.course.chat.data.remote.model.MessagesResponseModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagesApi {

    companion object {
        private const val MAX_NUM_BEFORE = 10
        private const val MAX_NUM_AFTER = 10

        private const val NEXT_NUM_BEFORE = 0
        const val NEXT_NUM_AFTER = 20

        private const val PREV_NUM_BEFORE = 20
        private const val PREV_NUM_AFTER = 0

        private const val ANCHOR_FIRST_UNREAD = "first_unread"
        const val ANCHOR_NEWEST = "newest"

        private const val TYPE_STREAM = "stream"
    }

    @GET("messages")
    suspend fun getChannelTopicMessages(
        @Query("narrow") narrow: String,
        @Query("anchor") anchor: String = ANCHOR_FIRST_UNREAD,
        @Query("num_before") numBefore: Int = MAX_NUM_BEFORE,
        @Query("num_after") numAfter: Int = MAX_NUM_AFTER,
        @Query("apply_markdown") applyMarkdown: Boolean = false,
    ): MessagesResponseModel

    @GET("messages")
    suspend fun getNextMessages(
        @Query("narrow") narrow: String,
        @Query("anchor") anchor: String,
        @Query("num_before") numBefore: Int = NEXT_NUM_BEFORE,
        @Query("num_after") numAfter: Int = NEXT_NUM_AFTER,
        @Query("apply_markdown") applyMarkdown: Boolean = false,
    ): MessagesResponseModel

    @GET("messages")
    suspend fun getPrevMessages(
        @Query("narrow") narrow: String,
        @Query("anchor") anchor: String,
        @Query("num_before") numBefore: Int = PREV_NUM_BEFORE,
        @Query("num_after") numAfter: Int = PREV_NUM_AFTER,
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