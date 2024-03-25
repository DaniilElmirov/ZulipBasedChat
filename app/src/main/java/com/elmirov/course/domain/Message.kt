package com.elmirov.course.domain

data class Message(
    val id: Int,
    val userId: Int,
    val data: String? = null,
    val authorName: String,
    val text: String,
    val reactions: Map<Int, Int>? = null
)
