package com.elmirov.course.domain.usecase

import com.elmirov.course.domain.entity.Result
import com.elmirov.course.domain.entity.User
import com.elmirov.course.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersWithError @Inject constructor(
    private val repository: UsersRepository,
) : suspend () -> Result<List<User>> by repository::getError