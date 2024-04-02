package com.elmirov.course.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentMainBinding
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.presentation.main.MainViewModel
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import javax.inject.Inject

class MainFragment : Fragment(), OnItemSelectedListener {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            viewModel.openChannels()
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
            R.id.channels -> viewModel.openChannels()

            R.id.people -> viewModel.openUsers()

            R.id.profile -> viewModel.openOwnProfile()
        }

        return true
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}