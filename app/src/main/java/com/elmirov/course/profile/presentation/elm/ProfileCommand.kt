package com.elmirov.course.profile.presentation.elm

sealed interface ProfileCommand {
    data object LoadOwn: ProfileCommand
}