package com.elmirov.course.profile.presentation

import com.elmirov.course.core.user.domain.entity.User

sealed interface ProfileState {

    data object Loading : ProfileState

    data class Content(val data: User) : ProfileState

    data object Error : ProfileState
}