package com.elmirov.course.di.profile.module

import com.elmirov.course.core.user.data.network.ProfileApi
import com.elmirov.course.core.user.domain.repository.OtherProfileRepository
import com.elmirov.course.core.user.domain.repository.OwnProfileRepository
import com.elmirov.course.di.profile.annotation.ProfileScope
import com.elmirov.course.profile.data.repository.OtherProfileRepositoryImpl
import com.elmirov.course.profile.data.repository.OwnProfileRepositoryImpl
import com.elmirov.course.profile.presentation.ProfileActor
import com.elmirov.course.profile.presentation.ProfileCommand
import com.elmirov.course.profile.presentation.ProfileEffect
import com.elmirov.course.profile.presentation.ProfileEvent
import com.elmirov.course.profile.presentation.ProfileReducer
import com.elmirov.course.profile.presentation.ProfileState
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import vivid.money.elmslie.core.store.ElmStore

@Module(includes = [BindProfileDataModule::class])
class ProfileDataModule {

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

    @Provides
    @ProfileScope
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create()
}

@Module
interface BindProfileDataModule {
    @Binds
    @ProfileScope
    fun bindOwnProfileRepository(impl: OwnProfileRepositoryImpl): OwnProfileRepository

    @Binds
    @ProfileScope
    fun bindOtherProfileRepository(impl: OtherProfileRepositoryImpl): OtherProfileRepository
}