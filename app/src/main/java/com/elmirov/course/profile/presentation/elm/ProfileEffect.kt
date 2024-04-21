package com.elmirov.course.profile.presentation.elm

sealed interface ProfileEffect {
    data object ShowError : ProfileEffect

    data object Back : ProfileEffect
}