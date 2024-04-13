package com.elmirov.course.navigation.router

import com.elmirov.course.navigation.Screens.ChatScreen
import com.elmirov.course.navigation.Screens.OwnProfileScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GlobalRouter {

    fun openOwnProfile()

    fun openChat(topicName: String)

    fun back()
}

class GlobalRouterImpl @Inject constructor(
    private val router: Router
) : GlobalRouter {

    override fun openOwnProfile() {
        router.navigateTo(OwnProfileScreen())
    }

    override fun openChat(topicName: String) {
        router.navigateTo(ChatScreen(topicName))
    }

    override fun back() {
        router.exit()
    }
}