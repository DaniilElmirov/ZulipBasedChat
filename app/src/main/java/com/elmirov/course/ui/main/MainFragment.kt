package com.elmirov.course.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentMainBinding
import com.elmirov.course.navigation.Screens.ChannelsScreen
import com.elmirov.course.navigation.Screens.ProfileScreen
import com.elmirov.course.navigation.Screens.UsersScreen
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import javax.inject.Inject

class MainFragment : Fragment(), OnItemSelectedListener {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var localRouter: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            localRouter.newRootScreen(ChannelsScreen())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener(this)

        val localNavigator =
            AppNavigator(requireActivity(), R.id.main_fragment_container, childFragmentManager)
        navigatorHolder.setNavigator(localNavigator)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.channels -> localRouter.navigateTo(ChannelsScreen())

            R.id.people -> localRouter.navigateTo(UsersScreen())

            R.id.profile -> localRouter.navigateTo(ProfileScreen())
        }

        return true
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}