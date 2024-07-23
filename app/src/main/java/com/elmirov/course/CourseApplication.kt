package com.elmirov.course

import android.app.Application
import com.elmirov.course.di.application.component.ApplicationComponent
import com.elmirov.course.di.application.component.DaggerApplicationComponent

open class CourseApplication : Application() {
    val component by lazy {
        initAppComponent()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    open fun initAppComponent(): ApplicationComponent =
        DaggerApplicationComponent.factory().create(applicationContext)
}