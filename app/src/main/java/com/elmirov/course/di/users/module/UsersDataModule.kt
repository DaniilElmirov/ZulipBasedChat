package com.elmirov.course.di.users.module

import com.elmirov.course.core.user.data.network.UsersApi
import com.elmirov.course.core.user.domain.repository.UsersRepository
import com.elmirov.course.di.users.annotation.UsersScope
import com.elmirov.course.users.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [BindUsersDataModule::class])
class UsersDataModule {

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