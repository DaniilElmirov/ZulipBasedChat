package com.elmirov.course.profile.presentation.elm

import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ProfileStoreFactory @Inject constructor(
    private val profileActor: ProfileActor,
) {

    fun create(): Store<ProfileEvent, ProfileEffect, ProfileState> =
        ElmStore(
            initialState = ProfileState(),
            reducer = ProfileReducer(),
            actor = profileActor
        )
}