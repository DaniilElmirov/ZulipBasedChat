package com.elmirov.course.profile.presentation

sealed interface ProfileState {

    data object Loading : ProfileState

    data object Content : ProfileState
}