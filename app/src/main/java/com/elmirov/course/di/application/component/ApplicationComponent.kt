package com.elmirov.course.di.application.component

import android.content.Context
import com.elmirov.course.CourseApplication
import com.elmirov.course.activity.ui.MainActivity
import com.elmirov.course.di.application.annotation.ApplicationScope
import com.elmirov.course.di.application.module.DispatcherModule
import com.elmirov.course.di.application.module.LocalDataModule
import com.elmirov.course.di.application.module.NavigationModule
import com.elmirov.course.di.application.module.RemoteDataModule
import com.elmirov.course.di.channels.component.ChannelsComponent
import com.elmirov.course.di.chat.component.ChatComponent
import com.elmirov.course.di.profile.component.ProfileComponent
import com.elmirov.course.di.users.component.UsersComponent
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
        RemoteDataModule::class,
        LocalDataModule::class,
        DispatcherModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: CourseApplication)

    fun inject(activity: MainActivity)

    fun chatComponentFactory(): ChatComponent.Factory

    fun channelsComponentFactory(): ChannelsComponent.Factory

    fun profileComponentFactory(): ProfileComponent.Factory

    fun usersComponentFactory(): UsersComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}