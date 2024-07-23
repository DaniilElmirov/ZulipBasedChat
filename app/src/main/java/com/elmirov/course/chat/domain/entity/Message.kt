package com.elmirov.course.chat.domain.entity

data class Message(
    val id: Int,
    val timestamp: Int,
    val topicName: String,
    val authorId: Int,
    val avatarUrl: String?,
    val authorName: String,
    val text: String,
    val reactions: Map<Reaction, ReactionParams>,
)