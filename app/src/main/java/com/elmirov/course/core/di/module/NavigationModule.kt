package com.elmirov.course.core.di.module

import com.elmirov.course.core.di.annotation.ApplicationScope
import com.elmirov.course.core.navigation.router.GlobalRouter
import com.elmirov.course.core.navigation.router.GlobalRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NavigationBindModule::class,
    ]
)
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @ApplicationScope
    fun provideRouter(): Router = cicerone.router

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
}

@Module
interface NavigationBindModule {

    @Binds
    @ApplicationScope
    fun bindGlobalRouter(impl: GlobalRouterImpl): GlobalRouter
}

