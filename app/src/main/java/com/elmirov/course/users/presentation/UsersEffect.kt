package com.elmirov.course.users.presentation

sealed interface UsersEffect {
    data object ShowError : UsersEffect
}