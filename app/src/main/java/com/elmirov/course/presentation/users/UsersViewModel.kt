package com.elmirov.course.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.domain.entity.Result
import com.elmirov.course.domain.usecase.GetUsersUseCase
import com.elmirov.course.navigation.router.GlobalRouter
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

    private fun loadUsers() {
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is Result.Error -> Unit
                is Result.Success -> _users.value = UsersState.Content(result.data)
            }
        }
    }

    fun openUserProfile() {
        router.openUserProfile()
    }
}