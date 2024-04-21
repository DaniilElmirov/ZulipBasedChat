package com.elmirov.course.profile.presentation

import androidx.lifecycle.ViewModel
import com.elmirov.course.navigation.router.GlobalRouter
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    fun back() {
        globalRouter.back()
    }
}