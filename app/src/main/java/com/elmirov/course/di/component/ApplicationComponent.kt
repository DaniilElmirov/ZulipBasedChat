package com.elmirov.course.di.component

import android.content.Context
import com.elmirov.course.CourseApplication
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.di.module.NavigationModule
import com.elmirov.course.di.module.ViewModelModule
import com.elmirov.course.ui.activity.MainActivity
import com.elmirov.course.ui.main.MainFragment
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

    //fun inject(fragment: MainFragment)

    fun inject(fragment: UsersFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}