package com.elmirov.course.domain

data class Message(
    val authorName: String,
    val text: String,
    val reactions: Map<Int, Int>
)
