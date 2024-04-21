package com.elmirov.course.profile.presentation.elm

import vivid.money.elmslie.core.store.dsl.ScreenDslReducer

class ProfileReducer : ScreenDslReducer<
        ProfileEvent,
        ProfileEvent.Ui,
        ProfileEvent.Internal,
        ProfileScreenState,
        ProfileEffect,
        ProfileCommand>(
    ProfileEvent.Ui::class, ProfileEvent.Internal::class
) {
    override fun Result.internal(event: ProfileEvent.Internal): Any = when (event) {
        ProfileEvent.Internal.ProfileLoadingError -> {
            state { copy(loading = false) }
            effects { +ProfileEffect.ShowError }
        }

        is ProfileEvent.Internal.ProfileLoadingSuccess -> {
            state { copy(loading = false, content = event.data) }
        }
    }

    override fun Result.ui(event: ProfileEvent.Ui): Any = when (event) {
        ProfileEvent.Ui.BackClick -> {
            effects { +ProfileEffect.Back }
        }

        ProfileEvent.Ui.Init -> {
            state { copy(loading = true) }
            commands { +ProfileCommand.LoadOwn }
        }
    }
}