package com.elmirov.course

import android.app.Application
import com.elmirov.course.di.application.component.DaggerApplicationComponent

class CourseApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}