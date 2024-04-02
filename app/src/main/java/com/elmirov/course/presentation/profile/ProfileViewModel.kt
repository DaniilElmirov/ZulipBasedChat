package com.elmirov.course.presentation.profile

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