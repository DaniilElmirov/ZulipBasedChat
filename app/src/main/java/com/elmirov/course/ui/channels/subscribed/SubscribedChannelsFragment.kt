package com.elmirov.course.ui.channels.subscribed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elmirov.course.CourseApplication
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.presentation.channels.subscribed.SubscribedChannelsViewModel
import com.elmirov.course.ui.adapter.MainAdapter
import com.elmirov.course.ui.channels.delegate.channel.ChannelDelegate
import com.elmirov.course.ui.channels.delegate.topic.TopicDelegate
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toDelegateItems
import javax.inject.Inject


class SubscribedChannelsFragment : Fragment() {

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

        binding.channels.adapter = subscribedChannelsAdapter

        collectLifecycleFlow(viewModel.subscribedChannels) {
            subscribedChannelsAdapter.submitList(it.data.toDelegateItems())
        }
    }

    override fun onDestroy() {
        binding.channels.adapter = null
        _binding = null
        super.onDestroy()
    }
}