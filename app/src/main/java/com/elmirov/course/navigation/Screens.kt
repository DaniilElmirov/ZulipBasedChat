package com.elmirov.course.navigation

import com.elmirov.course.ui.channels.ChannelsFragment
import com.elmirov.course.ui.chat.ChatFragment
import com.elmirov.course.ui.main.MainFragment
import com.elmirov.course.ui.profile.ProfileFragment
import com.elmirov.course.ui.users.UsersFragment
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

    fun ProfileScreen(own: Boolean): FragmentScreen = FragmentScreen {
        ProfileFragment.newInstance(own)
    }

    fun ChatScreen(): FragmentScreen = FragmentScreen {
        ChatFragment.newInstance()
    }
}