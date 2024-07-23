package com.elmirov.course.channels.domain.entity

data class Topic(
    val topicChannelId: Int,
    val name: String,
    val messageCount: Int,
)
