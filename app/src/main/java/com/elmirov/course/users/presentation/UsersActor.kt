package com.elmirov.course.users.presentation

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.users.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.store.Actor
import javax.inject.Inject

class UsersActor @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : Actor<UsersCommand, UsersEvent>() {

    override fun execute(command: UsersCommand): Flow<UsersEvent> = flow {
        when (command) {
            UsersCommand.Load -> {
                when (val result = getUsersUseCase()) {
                    is Result.Error -> emit(UsersEvent.Internal.UsersLoadingError)
                    is Result.Success -> emit(UsersEvent.Internal.UsersLoadingSuccess(result.data))
                }
            }
        }
    }
}