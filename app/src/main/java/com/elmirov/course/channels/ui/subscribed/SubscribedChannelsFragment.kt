package com.elmirov.course.channels.ui.subscribed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.presentation.subscribed.SubscribedChannelsState
import com.elmirov.course.channels.presentation.subscribed.SubscribedChannelsViewModel
import com.elmirov.course.channels.ui.communicator.SubscribedChannelsCommunicator
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegate
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.core.factory.ViewModelFactory
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toChannelDelegateItems
import javax.inject.Inject


class SubscribedChannelsFragment : Fragment(), SubscribedChannelsCommunicator {

    companion object {
        fun newInstance(): SubscribedChannelsFragment =
            SubscribedChannelsFragment()
    }

    private var _binding: FragmentPageChannelsBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SubscribedChannelsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val subscribedChannelsAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                ChannelDelegate(
                    showChannelTopics = viewModel::showTopics,
                    closeChannelTopics = viewModel::closeTopics,
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

        binding.channels.adapter = subscribedChannelsAdapter

        applyState()
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.subscribedChannels) { state ->
            when (state) {
                is SubscribedChannelsState.Content -> applyContent(state.data)

                SubscribedChannelsState.Loading -> applyLoading()

                SubscribedChannelsState.Error -> Unit //TODO добавить обработку стейта
            }
        }
    }

    private fun applyContent(data: List<Channel>) {
        subscribedChannelsAdapter.submitList(data.toChannelDelegateItems())

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

    override fun passSearchQueryInSubscribed(query: String) {
        viewModel.searchQueryPublisher.tryEmit(query)
    }
}