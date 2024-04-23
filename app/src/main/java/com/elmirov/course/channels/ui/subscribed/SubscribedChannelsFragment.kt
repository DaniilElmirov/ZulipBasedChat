package com.elmirov.course.channels.ui.subscribed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.elmirov.course.CourseApplication
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.presentation.ChannelsEffect
import com.elmirov.course.channels.presentation.ChannelsEvent
import com.elmirov.course.channels.presentation.ChannelsState
import com.elmirov.course.channels.presentation.ChannelsStoreFactory
import com.elmirov.course.channels.ui.communicator.SubscribedChannelsCommunicator
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegate
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.util.toDelegateItems
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject


class SubscribedChannelsFragment : ElmBaseFragment<ChannelsEffect, ChannelsState, ChannelsEvent>(),
    SubscribedChannelsCommunicator {

    companion object {
        fun newInstance(): SubscribedChannelsFragment =
            SubscribedChannelsFragment()
    }

    private var _binding: FragmentPageChannelsBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var channelsStoreFactory: ChannelsStoreFactory

    override val store: Store<ChannelsEvent, ChannelsEffect, ChannelsState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        channelsStoreFactory.create()
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val subscribedChannelsAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                ChannelDelegate(
                    showChannelTopics = {
                        store.accept(ChannelsEvent.Ui.OnChannelClick(it))
                    },
                    closeChannelTopics = {
                        store.accept(ChannelsEvent.Ui.OnChannelClick(it))
                    },
                )
            )
            addDelegate(
                TopicDelegate(
                    onTopicClick = { channelId, topicName ->
                        store.accept(ChannelsEvent.Ui.OnTopicClick(channelId, topicName))
                    }
                )
            )
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
        store.accept(ChannelsEvent.Ui.InitSubscribed)
    }

    override fun render(state: ChannelsState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    override fun handleEffect(effect: ChannelsEffect): Unit = when (effect) {
        ChannelsEffect.ShowError -> Unit //TODO обработка стейта
    }

    private fun applyContent(data: List<Channel>) {
        subscribedChannelsAdapter.submitList(data.toDelegateItems())

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
        //TODO придумать что-то с поиском
    }
}