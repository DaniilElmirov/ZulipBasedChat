package com.elmirov.course.profile.domain.repository

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User

interface OwnProfileRepository {

    suspend fun get(): Result<User>
}