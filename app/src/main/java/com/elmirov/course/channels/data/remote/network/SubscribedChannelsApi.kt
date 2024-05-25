package com.elmirov.course.channels.data.remote.network

import com.elmirov.course.channels.data.remote.model.SubscribedChannelsResponseModel
import com.elmirov.course.chat.data.remote.model.BaseResponseModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SubscribedChannelsApi {

    @GET("users/me/subscriptions")
    suspend fun get(): SubscribedChannelsResponseModel

    @POST("users/me/subscriptions")
    suspend fun create(@Query("subscriptions") subscriptions: String): BaseResponseModel
}