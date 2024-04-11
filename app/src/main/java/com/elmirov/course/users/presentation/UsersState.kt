package com.elmirov.course.users.presentation

import com.elmirov.course.users.domain.entity.User

sealed interface UsersState {

    data object Loading : UsersState

    data class Content(val data: List<User>) : UsersState

    data object Error: UsersState
}