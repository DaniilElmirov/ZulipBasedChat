package com.elmirov.course.navigation.router

import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.navigation.Screens.ChatScreen
import com.elmirov.course.navigation.Screens.OtherProfileScreen
import com.elmirov.course.navigation.Screens.OwnProfileScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GlobalRouter {

    fun openOwnProfile()

    fun openOtherProfile(userId: Int)

    fun openChat(chatInfo: ChatInfo)

    fun openChatTopic(chatInfo: ChatInfo)

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

    override fun openChat(chatInfo: ChatInfo) {
        router.navigateTo(ChatScreen(chatInfo))
    }

    override fun openChatTopic(chatInfo: ChatInfo) {
        router.newChain(ChatScreen(chatInfo))
    }

    override fun back() {
        router.exit()
    }
}