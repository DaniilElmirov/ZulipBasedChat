package com.elmirov.course.users.presentation

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class UsersStoreFactory @Inject constructor(
    private val usersActor: UsersActor,
    private val globalRouter: GlobalRouter,
) {

    fun create(): Store<UsersEvent, UsersEffect, UsersState> =
        ElmStore(
            initialState = UsersState(),
            reducer = UsersReducer(globalRouter),
            actor = usersActor
        )
}