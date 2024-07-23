package com.elmirov.course.core.user.data.network

import com.elmirov.course.core.user.data.model.UserModel
import com.elmirov.course.core.user.data.model.OtherProfileResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("users/me")
    suspend fun getOwn(): UserModel

    @GET("users/{user_id}")
    suspend fun getOtherById(@Path("user_id") id: Int): OtherProfileResponseModel
}