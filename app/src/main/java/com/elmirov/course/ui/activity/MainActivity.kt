package com.elmirov.course.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elmirov.course.CourseApplication
import com.elmirov.course.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CourseApplication).component.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}