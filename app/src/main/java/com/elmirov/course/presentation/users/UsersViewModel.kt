package com.elmirov.course.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val testData = listOf(
        User(
            id = 1,
            name = "Имя фамилия 1",
            mail = "почта1@gmail.com"
        ),
        User(
            id = 2,
            name = "Имя фамилия 2",
            mail = "почта2@gmail.com"
        ),
        User(
            id = 3,
            name = "Имя фамилия 3",
            mail = "почта3@gmail.com"
        ),
        User(
            id = 4,
            name = "Имя фамилия 4",
            mail = "почта4@gmail.com"
        ),
        User(
            id = 5,
            name = "Имя фамилия 5",
            mail = "почта5@gmail.com"
        ),
        User(
            id = 6,
            name = "Имя фамилия 6",
            mail = "почта6@gmail.com"
        ),
        User(
            id = 7,
            name = "Имя фамилия 7",
            mail = "почта7@gmail.com"
        ),
        User(
            id = 8,
            name = "Имя фамилия 8",
            mail = "почта8@gmail.com"
        ),
        User(
            id = 9,
            name = "Имя фамилия 9",
            mail = "почта9@gmail.com"
        ),
        User(
            id = 10,
            name = "Имя фамилия 10",
            mail = "почта10@gmail.com"
        ),
        User(
            id = 11,
            name = "Имя фамилия 11",
            mail = "почта11@gmail.com"
        ),
        User(
            id = 12,
            name = "Имя фамилия 12",
            mail = "почта12@gmail.com"
        ),
        User(
            id = 13,
            name = "Имя фамилия 13",
            mail = "почта13@gmail.com"
        ),
        User(
            id = 14,
            name = "Имя фамилия 14",
            mail = "почта14@gmail.com"
        ),
        User(
            id = 15,
            name = "Имя фамилия 15",
            mail = "почта15@gmail.com"
        ),
        User(
            id = 16,
            name = "Имя фамилия 16",
            mail = "почта16@gmail.com"
        ),
    )

    private val _users = MutableStateFlow<UsersState>(UsersState.Loading)
    val users = _users.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            delay(1000)
            _users.value = UsersState.Content(testData)
        }
    }
}