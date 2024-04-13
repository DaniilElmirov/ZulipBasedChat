package com.elmirov.course.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.navigation.router.GlobalRouter
import com.elmirov.course.users.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val router: GlobalRouter,
) : ViewModel() {

    private val _users = MutableStateFlow<UsersState>(UsersState.Loading)
    val users = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        _users.value = UsersState.Loading

        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is Result.Error -> _users.value = UsersState.Error

                is Result.Success -> _users.value = UsersState.Content(result.data)
            }
        }
    }

    fun openOtherProfile(userId: Int) {
        router.openOtherProfile(userId)
    }
}