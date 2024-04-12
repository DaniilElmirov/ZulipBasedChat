package com.elmirov.course.core.entity

data class User(
    val id: Int,
    val avatarUrl: String?,
    val name: String,
    val email: String,
)