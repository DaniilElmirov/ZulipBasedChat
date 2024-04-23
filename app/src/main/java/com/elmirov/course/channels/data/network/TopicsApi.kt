package com.elmirov.course.channels.data.network

import com.elmirov.course.channels.data.model.TopicsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicsApi {

    @GET("users/me/{stream_id}/topics")
    suspend fun getChannelTopics(@Path("stream_id") channelId: Int): TopicsResponseModel
}