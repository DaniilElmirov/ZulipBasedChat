package com.elmirov.course.navigation.router

import com.elmirov.course.navigation.Screens.ChatScreen
import com.elmirov.course.navigation.Screens.ProfileScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GlobalRouter {

    fun openUserProfile()

    fun openChat(topicName: String)
}

class GlobalRouterImpl @Inject constructor(
    private val router: Router
) : GlobalRouter {

    override fun openUserProfile() {
        router.navigateTo(ProfileScreen(own = false))
    }

    override fun openChat(topicName: String) {
        router.navigateTo(ChatScreen(topicName))
    }
}