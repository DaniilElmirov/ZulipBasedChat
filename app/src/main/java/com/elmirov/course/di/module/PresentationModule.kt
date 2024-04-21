package com.elmirov.course.di.module

import com.elmirov.course.di.annotation.ApplicationScope
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
class PresentationModule {

    @Provides
    @ApplicationScope
    fun provideUsersState(): UsersState = UsersState()

    @Provides
    @ApplicationScope
    fun provideUsersElmStore(
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