package com.elmirov.course.profile.data.model

import com.elmirov.course.core.user.data.model.UserModel
import com.google.gson.annotations.SerializedName

data class OtherProfileResponseModel(
    @SerializedName("msg") val message: String,
    val result: String,
    @SerializedName("user") val userModel: UserModel,
)
