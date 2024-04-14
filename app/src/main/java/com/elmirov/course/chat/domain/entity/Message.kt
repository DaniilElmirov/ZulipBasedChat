package com.elmirov.course.chat.domain.entity

data class Message(
    val id: Int,
    val date: String = "12.02.2002",
    val myMessage: Boolean,
    val avatarUrl: String?,
    val authorName: String,
    val text: String,
    val reactions: Map<Int, Int>? = null
)