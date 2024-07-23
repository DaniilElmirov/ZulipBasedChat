package com.elmirov.course.users.presentation

sealed interface UsersCommand {
    data object Load : UsersCommand
}