package com.elmirov.course.core.user.domain.repository

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User

interface UsersRepository {

    suspend fun get(): Result<List<User>>
}