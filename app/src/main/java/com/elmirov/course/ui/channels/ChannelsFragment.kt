package com.elmirov.course.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentChannelsBinding
import com.elmirov.course.ui.channels.all.AllChannelsFragment
import com.elmirov.course.ui.channels.subscribed.SubscribedChannelsFragment
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment() {

    companion object {

        private const val TAB_SUBSCRIBED = 0
        private const val TAB_ALL = 1

        fun newInstance(): ChannelsFragment =
            ChannelsFragment()
    }

    private var _binding: FragmentChannelsBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val fragments = listOf( //TODO порядок важен, можно ли как-то переделать?
            SubscribedChannelsFragment.newInstance(),
            AllChannelsFragment.newInstance()
        )
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle, fragments)
        binding.pager.adapter = pagerAdapter
    }

    private fun setupTabLayout() {
        tabLayoutMediator = TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            when (position) {
                TAB_SUBSCRIBED -> {
                    tab.text = getString(R.string.subscribed)
                }

                TAB_ALL -> {
                    tab.text = getString(R.string.all_streams)
                }

                else -> throw RuntimeException("ADD NEW TAB in ${javaClass.name}")
            }
        }
        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        tabLayoutMediator.detach()
        binding.pager.adapter = null
        _binding = null
        super.onDestroy()
    }
}