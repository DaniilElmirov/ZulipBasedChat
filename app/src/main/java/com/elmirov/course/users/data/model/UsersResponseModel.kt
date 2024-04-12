package com.elmirov.course.users.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponseModel(
    @SerializedName("members") val userModels: List<UserModel>
)
