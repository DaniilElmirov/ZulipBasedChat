package com.elmirov.course.profile.presentation.elm

import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ProfileStoreFactory @Inject constructor(
    private val profileActor: ProfileActor,
    private val globalRouter: GlobalRouter,
) {

    fun create(): Store<ProfileEvent, ProfileEffect, ProfileState> =
        ElmStore(
            initialState = ProfileState(),
            reducer = ProfileReducer(globalRouter),
            actor = profileActor
        )
}