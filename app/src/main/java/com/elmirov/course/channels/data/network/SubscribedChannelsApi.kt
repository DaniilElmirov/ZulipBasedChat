package com.elmirov.course.channels.data.network

import com.elmirov.course.channels.data.model.SubscribedChannelsResponseModel
import retrofit2.http.GET

interface SubscribedChannelsApi {

    @GET("users/me/subscriptions")
    suspend fun get(): SubscribedChannelsResponseModel
}