package com.elmirov.course.profile.data.network

import com.elmirov.course.core.user.data.model.UserModel
import retrofit2.http.GET

interface ProfileApi {

    @GET("users/me")
    suspend fun getOwn(): UserModel
}