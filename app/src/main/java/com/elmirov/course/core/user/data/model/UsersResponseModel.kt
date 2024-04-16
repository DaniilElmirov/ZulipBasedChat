package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponseModel(
    @SerializedName("members") val userModels: List<UserModel>
)
