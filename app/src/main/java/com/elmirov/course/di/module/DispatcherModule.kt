package com.elmirov.course.di.module

import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.di.annotation.DispatcherIo
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatcherModule {

    @Provides
    @ApplicationScope
    @DispatcherIo
    fun provideDispatcherIo(): CoroutineDispatcher = Dispatchers.IO
}