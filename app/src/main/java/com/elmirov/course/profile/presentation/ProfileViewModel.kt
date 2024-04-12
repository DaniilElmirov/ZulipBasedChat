package com.elmirov.course.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    init {
        loadProfile()
    }

    private val _profile = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profile = _profile.asStateFlow()

    fun back() {
        globalRouter.back()
    }

    private fun loadProfile() {
        viewModelScope.launch {

        }
    }
}