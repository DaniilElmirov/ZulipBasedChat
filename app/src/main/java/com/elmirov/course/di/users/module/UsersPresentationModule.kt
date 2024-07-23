package com.elmirov.course.di.users.module

import com.elmirov.course.di.users.annotation.UsersScope
import com.elmirov.course.users.presentation.UsersActor
import com.elmirov.course.users.presentation.UsersCommand
import com.elmirov.course.users.presentation.UsersEffect
import com.elmirov.course.users.presentation.UsersEvent
import com.elmirov.course.users.presentation.UsersReducer
import com.elmirov.course.users.presentation.UsersState
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.ElmStore

@Module
class UsersPresentationModule {

    @Provides
    @UsersScope
    fun provideUsersState(): UsersState = UsersState()

    @Provides
    @UsersScope
    fun provideUsersStore(
        usersState: UsersState,
        usersReducer: UsersReducer,
        usersActor: UsersActor
    ): ElmStore<UsersEvent, UsersState, UsersEffect, UsersCommand> =
        ElmStore(
            initialState = usersState,
            reducer = usersReducer,
            actor = usersActor,
        )
}