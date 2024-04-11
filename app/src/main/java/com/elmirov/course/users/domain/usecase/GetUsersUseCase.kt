package com.elmirov.course.users.domain.usecase

import com.elmirov.course.domain.entity.Result
import com.elmirov.course.users.domain.entity.User
import com.elmirov.course.users.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository,
) : suspend () -> Result<List<User>> by repository::get