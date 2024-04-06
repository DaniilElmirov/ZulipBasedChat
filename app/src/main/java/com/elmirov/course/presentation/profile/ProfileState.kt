package com.elmirov.course.presentation.profile

sealed interface ProfileState {

    data object Loading : ProfileState

    data object Content : ProfileState
}