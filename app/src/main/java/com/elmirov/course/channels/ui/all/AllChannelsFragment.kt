package com.elmirov.course.channels.ui.all

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
import com.elmirov.course.channels.ui.communicator.AllChannelsCommunicator
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegate
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.util.toDelegateItems
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class AllChannelsFragment : ElmBaseFragment<ChannelsEffect, ChannelsState, ChannelsEvent>(),
    AllChannelsCommunicator {

    companion object {
        fun newInstance(): AllChannelsFragment =
            AllChannelsFragment()
    }

    private var _binding: FragmentPageChannelsBinding? = null
    private val binding
        get() = _binding!!

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val allChannelsAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                ChannelDelegate(
                    onChannelClick = {
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

    @Inject
    lateinit var channelsStoreFactory: ChannelsStoreFactory

    override val store: Store<ChannelsEvent, ChannelsEffect, ChannelsState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        channelsStoreFactory.create()
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
        store.accept(ChannelsEvent.Ui.InitAll)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.refresh.setOnClickListener {
            store.accept(ChannelsEvent.Ui.OnRefreshAllClick)
        }
    }

    override fun render(state: ChannelsState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    override fun handleEffect(effect: ChannelsEffect): Unit = when (effect) {
        ChannelsEffect.ShowError -> applyError()
    }

    private fun applyContent(data: List<Channel>) {
        allChannelsAdapter.submitList(data.toDelegateItems())

        binding.apply {
            error.isVisible = false
            channels.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            error.isVisible = false
            channels.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun applyError() {
        binding.apply {
            error.isVisible = true
            channels.isVisible = false

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    override fun onDestroyView() {
        binding.channels.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun passSearchQueryInAll(query: String) {
        store.accept(ChannelsEvent.Ui.Search(query))
    }
}