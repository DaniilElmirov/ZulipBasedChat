package com.elmirov.course.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import com.elmirov.course.profile.domain.usecase.GetOtherProfileUseCase
import com.elmirov.course.profile.presentation.elm.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val getOtherProfileUseCase: GetOtherProfileUseCase,
) : ViewModel() {

    //TODO нужен initial стейт, и методы загрузки своего и чужого профиля

    private val _profile = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val profile = _profile.asStateFlow()

    fun loadOtherProfile(id: Int) {
        _profile.value = ProfileState.Loading

        viewModelScope.launch {
            when (val result = getOtherProfileUseCase(id)) {
                is Result.Error -> _profile.value = ProfileState.Error

                is Result.Success -> _profile.value = ProfileState.Content(result.data)
            }
        }
    }

    fun back() {
        globalRouter.back()
    }
}