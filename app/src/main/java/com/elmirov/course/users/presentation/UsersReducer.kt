package com.elmirov.course.users.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer
import javax.inject.Inject

class UsersReducer @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ScreenDslReducer<
        UsersEvent,
        UsersEvent.Ui,
        UsersEvent.Internal,
        UsersState,
        UsersEffect,
        UsersCommand>(
    UsersEvent.Ui::class, UsersEvent.Internal::class
) {
    override fun Result.internal(event: UsersEvent.Internal): Any = when (event) {
        UsersEvent.Internal.UsersLoadingError -> {
            state { copy(loading = false) }
            effects { +UsersEffect.ShowError }
        }

        is UsersEvent.Internal.UsersLoadingSuccess -> {
            state { copy(loading = false, content = event.data) }
        }
    }

    override fun Result.ui(event: UsersEvent.Ui): Any = when (event) {
        UsersEvent.Ui.Init -> {
            state { copy(loading = true) }
            commands { +UsersCommand.Load }
        }

        is UsersEvent.Ui.OnUserClick -> globalRouter.openOtherProfile(event.userId)

        UsersEvent.Ui.OnRefreshClick -> {
            state { copy(loading = true) }
            commands { +UsersCommand.Load }
        }
    }
}