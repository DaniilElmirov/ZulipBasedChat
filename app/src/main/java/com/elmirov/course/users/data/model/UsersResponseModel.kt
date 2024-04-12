package com.elmirov.course.users.data.model

import com.elmirov.course.core.user.data.model.UserModel
import com.google.gson.annotations.SerializedName

data class UsersResponseModel(
    @SerializedName("members") val userModels: List<UserModel>
)
