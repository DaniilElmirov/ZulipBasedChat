package com.elmirov.course.di.component

import android.content.Context
import com.elmirov.course.CourseApplication
import com.elmirov.course.activity.ui.MainActivity
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.di.channels.component.ChannelsComponent
import com.elmirov.course.di.chat.component.ChatComponent
import com.elmirov.course.di.module.DataModule
import com.elmirov.course.di.module.DispatcherModule
import com.elmirov.course.di.module.NavigationModule
import com.elmirov.course.profile.ui.OtherProfileFragment
import com.elmirov.course.profile.ui.OwnProfileFragment
import com.elmirov.course.users.ui.UsersFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
        DataModule::class,
        DispatcherModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: CourseApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: UsersFragment)

    fun inject(fragment: OwnProfileFragment)

    fun inject(fragment: OtherProfileFragment)

    fun chatComponentFactory(): ChatComponent.Factory

    fun channelsComponentFactory(): ChannelsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}