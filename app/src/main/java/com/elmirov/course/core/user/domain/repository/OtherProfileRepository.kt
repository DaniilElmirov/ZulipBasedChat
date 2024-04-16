package com.elmirov.course.core.user.domain.repository

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User

interface OtherProfileRepository {

   suspend fun getById(id: Int): Result<User>
}