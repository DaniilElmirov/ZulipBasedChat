package com.elmirov.course.domain

data class Channel(
    val id: Int,
    val name: String,
    val topics: List<Topic>? = null
)
