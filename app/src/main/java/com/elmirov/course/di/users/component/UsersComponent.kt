package com.elmirov.course.di.users.component

import com.elmirov.course.di.users.annotation.UsersScope
import com.elmirov.course.di.users.module.UsersDataModule
import com.elmirov.course.users.ui.UsersFragment
import dagger.Subcomponent

@UsersScope
@Subcomponent(
    modules = [
        UsersDataModule::class,
    ]
)
interface UsersComponent {

    fun inject(fragment: UsersFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): UsersComponent
    }
}