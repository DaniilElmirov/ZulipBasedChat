package com.elmirov.course.di.module

import com.elmirov.course.users.data.repository.UsersRepositoryImpl
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.users.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
}