package com.elmirov.course.di.users.module

import com.elmirov.course.core.user.data.network.UsersApi
import com.elmirov.course.core.user.domain.repository.UsersRepository
import com.elmirov.course.di.users.annotation.UsersScope
import com.elmirov.course.users.data.repository.UsersRepositoryImpl
import com.elmirov.course.users.presentation.UsersActor
import com.elmirov.course.users.presentation.UsersCommand
import com.elmirov.course.users.presentation.UsersEffect
import com.elmirov.course.users.presentation.UsersEvent
import com.elmirov.course.users.presentation.UsersReducer
import com.elmirov.course.users.presentation.UsersState
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import vivid.money.elmslie.core.store.ElmStore

@Module(includes = [BindUsersDataModule::class])
class UsersDataModule {

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

    @Provides
    @UsersScope
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()
}

@Module
interface BindUsersDataModule {
    @Binds
    @UsersScope
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
}