package com.elmirov.course.di.profile.component

import com.elmirov.course.di.profile.annotation.ProfileScope
import com.elmirov.course.di.profile.module.ProfilePresentationModule
import com.elmirov.course.profile.ui.OtherProfileFragment
import com.elmirov.course.profile.ui.OwnProfileFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(
    modules = [
        ProfilePresentationModule::class,
    ]
)
interface ProfileComponent {

    fun inject(fragment: OwnProfileFragment)

    fun inject(fragment: OtherProfileFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }
}