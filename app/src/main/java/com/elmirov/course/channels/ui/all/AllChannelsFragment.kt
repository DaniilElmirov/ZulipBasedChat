package com.elmirov.course.channels.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.channels.presentation.all.AllChannelsState
import com.elmirov.course.channels.presentation.all.AllChannelsViewModel
import com.elmirov.course.ui.adapter.MainAdapter
import com.elmirov.course.channels.ui.communicator.AllChannelsCommunicator
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegate
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegate
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toDelegateItems
import javax.inject.Inject

class AllChannelsFragment : Fragment(), AllChannelsCommunicator {

    companion object {
        fun newInstance(): AllChannelsFragment =
            AllChannelsFragment()
    }

    private var _binding: FragmentPageChannelsBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AllChannelsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val allChannelsAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                ChannelDelegate(
                    onArrowBottomClick = viewModel::showTopics,
                    onArrowTopClick = viewModel::closeTopics,
                )
            )
            addDelegate(TopicDelegate(viewModel::openChat))
        }
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPageChannelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.channels.adapter = allChannelsAdapter

        applyState()
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.allChannels) { state ->
            when (state) {
                is AllChannelsState.Content -> applyContent(state.data)

                AllChannelsState.Loading -> applyLoading()
            }
        }
    }

    private fun applyContent(data: List<Channel>) {
        allChannelsAdapter.submitList(data.toDelegateItems())

        binding.apply {
            channels.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            channels.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    override fun onDestroy() {
        binding.channels.adapter = null
        _binding = null
        super.onDestroy()
    }

    override fun passSearchQueryInAll(query: String) {
        viewModel.searchQueryPublisher.tryEmit(query)
    }
}