package com.elmirov.course.di.profile.module

import com.elmirov.course.di.profile.annotation.ProfileScope
import com.elmirov.course.profile.presentation.ProfileActor
import com.elmirov.course.profile.presentation.ProfileCommand
import com.elmirov.course.profile.presentation.ProfileEffect
import com.elmirov.course.profile.presentation.ProfileEvent
import com.elmirov.course.profile.presentation.ProfileReducer
import com.elmirov.course.profile.presentation.ProfileState
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.core.store.ElmStore

@Module
class ProfilePresentationModule {

    @Provides
    @ProfileScope
    fun provideProfileState(): ProfileState = ProfileState()

    @Provides
    @ProfileScope
    fun provideProfileStore(
        profileState: ProfileState,
        profileReducer: ProfileReducer,
        profileActor: ProfileActor
    ): ElmStore<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand> =
        ElmStore(
            initialState = profileState,
            reducer = profileReducer,
            actor = profileActor,
        )
}