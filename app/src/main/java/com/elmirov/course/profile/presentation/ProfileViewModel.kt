package com.elmirov.course.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import com.elmirov.course.profile.domain.usecase.GetOwnProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val getOwnProfileUseCase: GetOwnProfileUseCase,
) : ViewModel() {

    //TODO нужен initial стейт, и методы загрузки своего и чужого профиля

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
            when (val result = getOwnProfileUseCase()) {
                is Result.Error -> _profile.value = ProfileState.Error

                is Result.Success -> _profile.value = ProfileState.Content(result.data)
            }
        }
    }
}