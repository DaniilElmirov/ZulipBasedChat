package com.elmirov.course.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.databinding.ActivityMainBinding
import com.elmirov.course.navigation.Screens
import com.elmirov.course.ui.channels.AllChannelsCommunicator
import com.elmirov.course.ui.channels.ChannelsFragment
import com.elmirov.course.ui.channels.SubscribedChannelsCommunicator
import com.elmirov.course.ui.channels.all.AllChannelsFragment
import com.elmirov.course.ui.channels.subscribed.SubscribedChannelsFragment
import com.elmirov.course.ui.main.MainFragment
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SubscribedChannelsCommunicator, AllChannelsCommunicator {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CourseApplication).component.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            router.newRootScreen(Screens.MainScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun passSearchQueryInSubscribed(query: String) {

        getSubscribedChannelsFragment().passSearchQueryInSubscribed(query)
    }

    override fun passSearchQueryInAll(query: String) {

        getAllChannelsFragment().passSearchQueryInAll(query)
    }

    private fun getSubscribedChannelsFragment(): SubscribedChannelsFragment {
        val channelsFragment = findChannelsFragment()

        val subscribed = channelsFragment.childFragmentManager.fragments.find {
            it is SubscribedChannelsFragment
        } as SubscribedChannelsFragment

        return subscribed
    }

    private fun getAllChannelsFragment(): AllChannelsFragment {
        val channelsFragment = findChannelsFragment()

        val all = channelsFragment.childFragmentManager.fragments.find {
            it is AllChannelsFragment
        } as AllChannelsFragment

        return all
    }

    private fun findChannelsFragment(): ChannelsFragment =
        binding.root.getFragment<MainFragment>().childFragmentManager.fragments.find {
            it is ChannelsFragment
        } as ChannelsFragment
}