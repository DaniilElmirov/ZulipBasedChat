package com.elmirov.course.profile.domain.usecase

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.core.user.domain.repository.OwnProfileRepository
import javax.inject.Inject

class GetOwnProfileUseCase @Inject constructor(
    private val repository: OwnProfileRepository,
) : suspend () -> Result<User> by repository::get