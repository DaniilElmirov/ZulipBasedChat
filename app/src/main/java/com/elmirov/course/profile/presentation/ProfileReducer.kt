package com.elmirov.course.profile.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer
import javax.inject.Inject

class ProfileReducer @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ScreenDslReducer<
        ProfileEvent,
        ProfileEvent.Ui,
        ProfileEvent.Internal,
        ProfileState,
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

        ProfileEvent.Ui.BackClick -> globalRouter.back()

        ProfileEvent.Ui.InitOwn -> {
            state { copy(loading = true) }
            commands { +ProfileCommand.LoadOwn }
        }

        is ProfileEvent.Ui.InitOther -> {
            state { copy(loading = true) }
            commands { +ProfileCommand.LoadOther(event.userId) }
        }

        ProfileEvent.Ui.OnRefreshOwnClick -> {
            state { copy(loading = true) }
            commands { +ProfileCommand.LoadOwn }
        }

        is ProfileEvent.Ui.OnRefreshOtherClick -> {
            state { copy(loading = true) }
            commands { +ProfileCommand.LoadOther(event.userId) }
        }
    }
}