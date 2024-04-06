package com.elmirov.course.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.delay
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
            delay(1000)
            _profile.value = ProfileState.Content
        }
    }
}