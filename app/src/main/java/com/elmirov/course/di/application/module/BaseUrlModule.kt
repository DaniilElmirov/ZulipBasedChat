package com.elmirov.course.di.application.module

import com.elmirov.course.di.application.annotation.ApplicationScope
import com.elmirov.course.di.application.annotation.BaseUrl
import dagger.Module
import dagger.Provides

@Module
class BaseUrlModule {

    private companion object {

        private const val BASE_URL = "https://tinkoff-android-spring-2024.zulipchat.com/api/v1/"
    }

    @Provides
    @ApplicationScope
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL
}