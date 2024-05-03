package com.elmirov.course.chat.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReactionModel(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("emoji_name") val name: String,
    @SerializedName("emoji_code") val code: String,
    @SerializedName("reaction_type") val reactionType: String,
) {
    companion object {
        const val REACTION_TYPE_UNICODE_EMOJI = "unicode_emoji"
        const val STUB_EMOJI_CODE = "1f921"
    }
}