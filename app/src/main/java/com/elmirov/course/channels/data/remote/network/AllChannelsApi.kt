package com.elmirov.course.channels.data.remote.network

import com.elmirov.course.channels.data.remote.model.AllChannelsResponseModel
import retrofit2.http.GET

interface AllChannelsApi {

    @GET("streams")
    suspend fun get(): AllChannelsResponseModel
}