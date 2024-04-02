package com.elmirov.course.navigation.router

import com.elmirov.course.navigation.Screens.ProfileScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GlobalRouter {

    fun openUserProfile()
}

class GlobalRouterImpl @Inject constructor(
    private val router: Router
) : GlobalRouter {

    override fun openUserProfile() {
        router.navigateTo(ProfileScreen(own = false))
    }
}