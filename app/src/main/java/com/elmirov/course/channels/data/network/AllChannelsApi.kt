package com.elmirov.course.channels.data.network

import com.elmirov.course.channels.data.model.AllChannelsResponseModel
import retrofit2.http.GET

interface AllChannelsApi {

    @GET("streams")
    suspend fun get(): AllChannelsResponseModel
}