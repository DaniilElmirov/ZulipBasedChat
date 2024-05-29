package com.elmirov.course.channels.domain.entity

data class Channel(
    val id: Int,
    val name: String,
    val expanded: Boolean = false,
    val topics: List<Topic>? = null,
)
