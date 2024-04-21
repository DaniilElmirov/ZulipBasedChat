package com.elmirov.course.profile.presentation.elm

import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.profile.domain.usecase.GetOwnProfileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.store.Actor
import javax.inject.Inject

class ProfileActor @Inject constructor(
    private val getOwnProfileUseCase: GetOwnProfileUseCase,
): Actor<ProfileCommand, ProfileEvent>() {

    override fun execute(command: ProfileCommand): Flow<ProfileEvent> = flow {
        when(command) {
            ProfileCommand.LoadOwn -> {
                when(val result = getOwnProfileUseCase()) {
                    is Result.Error -> emit(ProfileEvent.Internal.ProfileLoadingError)
                    is Result.Success -> emit(ProfileEvent.Internal.ProfileLoadingSuccess(result.data))
                }
            }
        }
    }
}