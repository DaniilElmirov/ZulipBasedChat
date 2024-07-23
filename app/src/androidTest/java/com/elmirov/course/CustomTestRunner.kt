package com.elmirov.course

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.elmirov.course.di.TestApplication

class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}