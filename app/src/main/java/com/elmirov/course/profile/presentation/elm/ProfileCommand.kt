package com.elmirov.course.profile.presentation.elm

sealed interface ProfileCommand {
    data object LoadOwn : ProfileCommand

    data class LoadOther(val userId: Int) : ProfileCommand
}