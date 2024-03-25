package com.elmirov.course.domain

import java.util.UUID

data class Message(
    val id: UUID = UUID.randomUUID(),
    val userId: Int,
    val data: String? = null,
    val authorName: String,
    val text: String,
    val reactions: Map<Int, Int>? = null
)
