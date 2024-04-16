package com.elmirov.course.profile.domain.usecase

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.core.user.domain.entity.User
import com.elmirov.course.core.user.domain.repository.OtherProfileRepository
import javax.inject.Inject

class GetOtherProfileUseCase @Inject constructor(
    private val repository: OtherProfileRepository,
) : suspend (Int) -> Result<User> by repository::getById