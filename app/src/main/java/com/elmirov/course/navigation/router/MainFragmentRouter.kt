package com.elmirov.course.navigation.router

import com.elmirov.course.navigation.Screens.ChannelsScreen
import com.elmirov.course.navigation.Screens.ProfileScreen
import com.elmirov.course.navigation.Screens.UsersScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface MainFragmentRouter {

    fun openChannels()

    fun openUsers()

    fun openOwnProfile()
}

class MainFragmentRouterImpl @Inject constructor(
    private val localRouter: Router,
) : MainFragmentRouter {

    override fun openChannels() {
        localRouter.navigateTo(ChannelsScreen())
    }

    override fun openOwnProfile() {
        localRouter.navigateTo(ProfileScreen(own = true))
    }

    override fun openUsers() {
        localRouter.navigateTo(UsersScreen())
    }
}