package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class OtherProfileResponseModel(
    @SerializedName("msg") val message: String,
    val result: String,
    @SerializedName("user") val userModel: UserModel,
)
