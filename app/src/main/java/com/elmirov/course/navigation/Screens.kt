package com.elmirov.course.navigation

import com.elmirov.course.ui.channels.ChannelsFragment
import com.elmirov.course.ui.profile.ProfileFragment
import com.elmirov.course.ui.users.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun ChannelsScreen(): FragmentScreen = FragmentScreen {
        ChannelsFragment.newInstance()
    }

    fun UsersScreen(): FragmentScreen = FragmentScreen {
        UsersFragment.newInstance()
    }

    fun ProfileScreen(): FragmentScreen = FragmentScreen {
        ProfileFragment.newInstance()
    }
}