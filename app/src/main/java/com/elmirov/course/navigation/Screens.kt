package com.elmirov.course.navigation

import com.elmirov.course.channels.ui.ChannelsFragment
import com.elmirov.course.chat.ui.ChatFragment
import com.elmirov.course.main.ui.MainFragment
import com.elmirov.course.profile.ui.OtherProfileFragment
import com.elmirov.course.profile.ui.OwnProfileFragment
import com.elmirov.course.users.ui.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun MainScreen(): FragmentScreen = FragmentScreen {
        MainFragment.newInstance()
    }

    fun ChannelsScreen(): FragmentScreen = FragmentScreen {
        ChannelsFragment.newInstance()
    }

    fun UsersScreen(): FragmentScreen = FragmentScreen {
        UsersFragment.newInstance()
    }

    fun OwnProfileScreen(): FragmentScreen = FragmentScreen {
        OwnProfileFragment.newInstance()
    }

    fun OtherProfileScreen(id: Int): FragmentScreen = FragmentScreen {
        OtherProfileFragment.newInstance(id)
    }

    fun ChatScreen(topicChannelName: String, topicName: String, ownId: Int): FragmentScreen =
        FragmentScreen {
            ChatFragment.newInstance(topicChannelName, topicName, ownId)
        }
}