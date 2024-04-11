package com.elmirov.course.core.di.module

import com.elmirov.course.core.di.annotation.ApplicationScope
import com.elmirov.course.core.di.annotation.DispatcherIo
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