package com.elmirov.course.di.component

import android.content.Context
import com.elmirov.course.CourseApplication
import com.elmirov.course.di.annotation.ApplicationScope
import com.elmirov.course.di.module.NavigationModule
import com.elmirov.course.ui.activity.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NavigationModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application:CourseApplication)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}