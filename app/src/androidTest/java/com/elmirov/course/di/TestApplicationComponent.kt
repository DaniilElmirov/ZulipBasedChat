package com.elmirov.course.di

import android.content.Context
import com.elmirov.course.di.application.annotation.ApplicationScope
import com.elmirov.course.di.application.annotation.BaseUrl
import com.elmirov.course.di.application.component.ApplicationComponent
import com.elmirov.course.di.application.module.DispatcherModule
import com.elmirov.course.di.application.module.LocalDataModule
import com.elmirov.course.di.application.module.NavigationModule
import com.elmirov.course.di.application.module.RemoteDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
        RemoteDataModule::class,
        LocalDataModule::class,
        DispatcherModule::class,
        TestBaseUrlModule::class,
    ]
)
interface TestApplicationComponent : ApplicationComponent {

    fun inject(application: TestApplication)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): TestApplicationComponent
    }
}

@Module
class TestBaseUrlModule {
    private companion object {

        private const val TEST_BASE_URL = "http://localhost:8080"
    }

    @Provides
    @ApplicationScope
    @BaseUrl
    fun provideTestBaseUrl(): String = TEST_BASE_URL
}