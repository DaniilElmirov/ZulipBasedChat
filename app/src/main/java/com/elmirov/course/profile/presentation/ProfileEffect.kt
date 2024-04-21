package com.elmirov.course.profile.presentation

sealed interface ProfileEffect {
    data object ShowError : ProfileEffect
}