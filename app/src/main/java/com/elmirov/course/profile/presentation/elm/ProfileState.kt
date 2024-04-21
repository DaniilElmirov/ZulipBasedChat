package com.elmirov.course.profile.presentation.elm

import com.elmirov.course.core.user.domain.entity.User

sealed interface ProfileState {

    data object Initial : ProfileState

    data object Loading : ProfileState

    data class Content(val data: User) : ProfileState

    data object Error : ProfileState
}

data class ProfileScreenState(
    val loading: Boolean = false,
    val content: User? = null,
)