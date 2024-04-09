package com.elmirov.course.presentation.users

import com.elmirov.course.domain.entity.User

sealed interface UsersState {

    data object Loading : UsersState

    data class Content(val data: List<User>) : UsersState

    data object Error: UsersState
}