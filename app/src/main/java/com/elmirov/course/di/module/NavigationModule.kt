package com.elmirov.course.di.module

import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.navigation.router.MainFragmentRouter
import com.elmirov.course.navigation.router.MainFragmentRouterImpl
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
    fun bindMainFragmentRouter(impl: MainFragmentRouterImpl): MainFragmentRouter
}

