package com.elmirov.course.presentation.main

import androidx.lifecycle.ViewModel
import com.elmirov.course.navigation.router.MainFragmentRouter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: MainFragmentRouter,
) : ViewModel() {

    fun openChannels() {
        router.openChannels()
    }

    fun openUsers() {
        router.openUsers()
    }

    fun openOwnProfile() {
        router.openOwnProfile()
    }
}