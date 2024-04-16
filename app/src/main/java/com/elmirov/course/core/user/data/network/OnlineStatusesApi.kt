package com.elmirov.course.core.user.data.network

import com.elmirov.course.core.user.data.model.AllUsersOnlineStatusResponseModel
import com.elmirov.course.core.user.data.model.UserOnlineStatusResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface OnlineStatusesApi {

    @GET("realm/presence")
    suspend fun getAll(): AllUsersOnlineStatusResponseModel

    @GET("users/{user_id_or_email}/presence")
    suspend fun getById(
        @Path("user_id_or_email") userId: Int
    ): UserOnlineStatusResponseModel
}