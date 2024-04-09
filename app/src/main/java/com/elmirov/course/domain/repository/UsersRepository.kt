package com.elmirov.course.domain.repository

import com.elmirov.course.domain.entity.Result
import com.elmirov.course.domain.entity.User

interface UsersRepository {

    suspend fun get(): Result<List<User>>
}