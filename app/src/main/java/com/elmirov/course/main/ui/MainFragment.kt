package com.elmirov.course.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentMainBinding
import com.elmirov.course.navigation.Screens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class MainFragment : Fragment(), OnItemSelectedListener {

    companion object {
        fun newInstance(): MainFragment =
            MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var localRouter: Router
    private lateinit var localNavigatorHolder: NavigatorHolder
    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cicerone = Cicerone.create()
        localRouter = cicerone.router
        localNavigatorHolder = cicerone.getNavigatorHolder()

        if (savedInstanceState == null)
            localRouter.newRootScreen(Screens.ChannelsScreen())
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
        setupBackPressedCallback()
    }

    private fun setupBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (binding.bottomNavigation.selectedItemId) {
                    R.id.channels -> localRouter.exit()
                    R.id.people, R.id.profile -> {
                        binding.bottomNavigation.selectedItemId = R.id.channels
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onResume() {
        super.onResume()

        val localNavigator =
            AppNavigator(requireActivity(), R.id.main_fragment_container, childFragmentManager)
        localNavigatorHolder.setNavigator(localNavigator)
    }

    override fun onPause() {
        super.onPause()

        localNavigatorHolder.removeNavigator()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (binding.bottomNavigation.selectedItemId != item.itemId) {
            when (item.itemId) {
                R.id.channels -> localRouter.backTo(Screens.ChannelsScreen())

                R.id.people -> localRouter.navigateTo(Screens.UsersScreen())

                R.id.profile -> localRouter.navigateTo(Screens.OwnProfileScreen())
            }
        }

        return true
    }

    override fun onDestroyView() {
        backPressedCallback.remove()
        _binding = null
        super.onDestroyView()
    }
}