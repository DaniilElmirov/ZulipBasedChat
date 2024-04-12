package com.elmirov.course.users.data.network

import com.elmirov.course.users.data.model.UsersResponseModel
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun get(): UsersResponseModel
}