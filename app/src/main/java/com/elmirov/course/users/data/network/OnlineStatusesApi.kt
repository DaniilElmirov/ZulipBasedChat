package com.elmirov.course.users.data.network

import com.elmirov.course.users.data.model.AllUsersOnlineStatusResponseModel
import retrofit2.http.GET

interface OnlineStatusesApi {

    @GET("realm/presence")
    suspend fun getAll(): AllUsersOnlineStatusResponseModel
}