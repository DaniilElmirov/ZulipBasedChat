package com.elmirov.course.channels.domain.entity

data class Channel(
    val id: Int,
    val name: String,
    val expanded: Boolean = false, //TODO возможно плохой подход в совокупности с методами vm
    val topics: List<Topic>? = null,
)
