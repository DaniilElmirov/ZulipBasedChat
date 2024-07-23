package com.elmirov.course.users.presentation

import com.elmirov.course.core.user.domain.entity.User

sealed interface UsersEvent {

    sealed interface Ui : UsersEvent {
        data object Init : Ui

        data class OnUserClick(val userId: Int) : Ui

        data object OnRefreshClick : Ui
    }

    sealed interface Internal : UsersEvent {
        data class UsersLoadingSuccess(val data: List<User>) : Internal

        data object UsersLoadingError : Internal
    }
}