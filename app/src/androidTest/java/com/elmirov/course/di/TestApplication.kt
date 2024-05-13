package com.elmirov.course.di

import com.elmirov.course.CourseApplication
import com.elmirov.course.di.application.component.ApplicationComponent

class TestApplication: CourseApplication() {

    override fun initAppComponent(): ApplicationComponent {
        return DaggerTestApplicationComponent.factory().create(applicationContext)
    }
}