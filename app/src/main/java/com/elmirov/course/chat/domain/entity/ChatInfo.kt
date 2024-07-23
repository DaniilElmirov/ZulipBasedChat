package com.elmirov.course.chat.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatInfo(
    val channelId: Int,
    val channelName: String,
    val topicName: String,
): Parcelable