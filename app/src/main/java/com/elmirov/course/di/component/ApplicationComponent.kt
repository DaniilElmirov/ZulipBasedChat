package com.elmirov.course.di.component

import android.content.Context
import com.elmirov.course.CourseApplication
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.di.module.NavigationModule
import com.elmirov.course.di.module.ViewModelModule
import com.elmirov.course.ui.activity.MainActivity
import com.elmirov.course.ui.channels.all.AllChannelsFragment
import com.elmirov.course.ui.channels.subscribed.SubscribedChannelsFragment
import com.elmirov.course.ui.chat.ChatFragment
import com.elmirov.course.ui.profile.ProfileFragment
import com.elmirov.course.ui.users.UsersFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: CourseApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: UsersFragment)

    fun inject(fragment: AllChannelsFragment)

    fun inject(fragment: SubscribedChannelsFragment)

    fun inject(fragment: ChatFragment)

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}