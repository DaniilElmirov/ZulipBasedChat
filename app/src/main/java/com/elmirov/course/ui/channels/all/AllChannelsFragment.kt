package com.elmirov.course.ui.channels.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elmirov.course.databinding.FragmentPageChannelsBinding
import com.elmirov.course.presentation.channels.all.AllChannelsViewModel
import com.elmirov.course.ui.channels.adapter.ChannelsAdapter
import com.elmirov.course.util.collectLifecycleFlow

class AllChannelsFragment : Fragment() {

    companion object {
        fun newInstance(): AllChannelsFragment =
            AllChannelsFragment()
    }

    private var _binding: FragmentPageChannelsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: AllChannelsViewModel by viewModels()

    private val allChannelsAdapter by lazy {
        ChannelsAdapter()
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

        collectLifecycleFlow(viewModel.allChannels) {
            allChannelsAdapter.submitList(it.data)
        }
    }

    override fun onDestroy() {
        binding.channels.adapter = null
        _binding = null
        super.onDestroy()
    }
}