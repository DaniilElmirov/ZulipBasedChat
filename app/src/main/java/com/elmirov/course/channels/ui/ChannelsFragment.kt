package com.elmirov.course.channels.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.elmirov.course.R
import com.elmirov.course.channels.ui.all.AllChannelsFragment
import com.elmirov.course.channels.ui.communicator.AllChannelsCommunicator
import com.elmirov.course.channels.ui.communicator.SubscribedChannelsCommunicator
import com.elmirov.course.channels.ui.subscribed.SubscribedChannelsFragment
import com.elmirov.course.databinding.FragmentChannelsBinding
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment(), SubscribedChannelsCommunicator, AllChannelsCommunicator {

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

    private lateinit var pageChangeCallback: OnPageChangeCallback

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

        initStatusBar()
        setupViewPager()
        setupTabLayout()
        setupOnPageChangeCallback()
    }

    private fun initStatusBar() {
        requireActivity().window.statusBarColor = requireContext()
            .getColor(R.color.background_100)
    }

    private fun setupViewPager() {
        val fragments = listOf(
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
        }.apply { attach() }
    }

    private fun setupOnPageChangeCallback() {
        pageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    TAB_SUBSCRIBED -> setSubscribedTextChangeListener()

                    TAB_ALL -> setAllTextChangeListener()
                }
            }
        }

        binding.pager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun setSubscribedTextChangeListener() {
        binding.search.doOnTextChanged { text, _, _, _ ->
            passSearchQueryInSubscribed(text.toString().trim())
        }
    }

    private fun setAllTextChangeListener() {
        binding.search.doOnTextChanged { text, _, _, _ ->
            passSearchQueryInAll(text.toString().trim())
        }
    }

    override fun passSearchQueryInSubscribed(query: String) {
        getSubscribedFragment().passSearchQueryInSubscribed(query)
    }

    override fun passSearchQueryInAll(query: String) {
        getAllFragment().passSearchQueryInAll(query)
    }

    private fun getSubscribedFragment(): SubscribedChannelsFragment =
        childFragmentManager.fragments.find {
            it is SubscribedChannelsFragment
        } as SubscribedChannelsFragment

    private fun getAllFragment(): AllChannelsFragment =
        childFragmentManager.fragments.find {
            it is AllChannelsFragment
        } as AllChannelsFragment

    override fun onDestroyView() {
        tabLayoutMediator.detach()
        binding.pager.unregisterOnPageChangeCallback(pageChangeCallback)
        binding.pager.adapter = null
        _binding = null
        super.onDestroyView()
    }
}