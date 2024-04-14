package com.elmirov.course.navigation.router

import com.elmirov.course.navigation.Screens.ChatScreen
import com.elmirov.course.navigation.Screens.OtherProfileScreen
import com.elmirov.course.navigation.Screens.OwnProfileScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GlobalRouter {

    fun openOwnProfile()

    fun openOtherProfile(userId: Int)

    fun openChat(topicChannelName: String, topicName: String, ownId: Int)

    fun back()
}

class GlobalRouterImpl @Inject constructor(
    private val router: Router
) : GlobalRouter {

    override fun openOwnProfile() {
        router.navigateTo(OwnProfileScreen())
    }

    override fun openOtherProfile(userId: Int) {
        router.navigateTo(OtherProfileScreen(userId))
    }

    override fun openChat(topicChannelName: String, topicName: String, ownId: Int) {
        router.navigateTo(ChatScreen(topicChannelName, topicName, ownId))
    }

    override fun back() {
        router.exit()
    }
}