package com.elmirov.course.profile.presentation.elm

import com.elmirov.course.core.user.domain.entity.User

sealed interface ProfileEvent {

    sealed interface Ui : ProfileEvent {
        data object InitOwn : Ui

        data class InitOther(val userId: Int) : Ui

        data object BackClick : Ui
    }

    sealed interface Internal : ProfileEvent {
        data class ProfileLoadingSuccess(val data: User) : Internal

        data object ProfileLoadingError : Internal
    }
}