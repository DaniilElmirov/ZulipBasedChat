package com.elmirov.course.channels.ui.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.channels.domain.entity.Channel
import com.elmirov.course.channels.presentation.ChannelsCommand
import com.elmirov.course.channels.presentation.ChannelsEffect
import com.elmirov.course.channels.presentation.ChannelsEvent
import com.elmirov.course.channels.presentation.ChannelsState
import com.elmirov.course.channels.ui.communicator.AllChannelsCommunicator
import com.elmirov.course.channels.ui.delegate.channel.ChannelDelegate
import com.elmirov.course.channels.ui.delegate.topic.TopicDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.util.getErrorSnackBar
import com.elmirov.course.util.toDelegateItems
import com.google.android.material.snackbar.Snackbar
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.ElmStore
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
        (requireActivity().application as CourseApplication).component.channelsComponentFactory()
            .create()
    }

    @Inject
    lateinit var channelsStore: ElmStore<ChannelsEvent, ChannelsState, ChannelsEffect, ChannelsCommand>

    override val store: Store<ChannelsEvent, ChannelsEffect, ChannelsState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        channelsStore
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

    private var errorSnackBar: Snackbar? = null

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

    override fun passSearchQueryInAll(query: String) {
        store.accept(ChannelsEvent.Ui.Search(query))
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

    private fun applyError() {
        binding.apply {
            channels.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }

        errorSnackBar = getErrorSnackBar(
            textResId = R.string.unknown_error,
            actionText = getString(R.string.try_again),
            actionListener = {store.accept(ChannelsEvent.Ui.OnRefreshAllClick)}
        )
        errorSnackBar?.anchorView = binding.snakbarAnchor
        errorSnackBar?.show()
    }

    override fun onDestroyView() {
        binding.channels.adapter = null
        _binding = null
        errorSnackBar?.dismiss()
        errorSnackBar = null
        super.onDestroyView()
    }
}