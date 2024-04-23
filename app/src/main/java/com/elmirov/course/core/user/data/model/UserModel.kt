package com.elmirov.course.core.user.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("user_id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("full_name") val name: String,
    val email: String,
)
