package com.elmirov.course.core.user.data.network

import com.elmirov.course.core.user.data.model.UsersResponseModel
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun get(): UsersResponseModel
}