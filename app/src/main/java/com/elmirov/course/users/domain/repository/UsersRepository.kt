package com.elmirov.course.users.domain.repository

import com.elmirov.course.core.entity.Result
import com.elmirov.course.users.domain.entity.User

interface UsersRepository {

    suspend fun get(): Result<List<User>>
}