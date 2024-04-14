package com.elmirov.course.chat.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.presentation.ChatState
import com.elmirov.course.chat.presentation.ChatViewModel
import com.elmirov.course.chat.ui.delegate.date.DateDelegate
import com.elmirov.course.chat.ui.delegate.incoming.IncomingMessageDelegate
import com.elmirov.course.chat.ui.delegate.outgoing.OutgoingMessageDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.core.factory.ViewModelFactory
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toDelegateItems
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ChatFragment : Fragment() {

    companion object {

        //TODO временное решение, лучше личный id хранить в локальном хранилище
        private const val OWN_ID = 709286

        private const val KEY_TOPIC_CHANNEL_NAME = "KEY_TOPIC_CHANNEL_NAME"
        private const val KEY_TOPIC_NAME = "KEY_TOPIC_NAME"

        private const val EMPTY_STRING = ""

        fun newInstance(topicChannelName: String, topicName: String): ChatFragment =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TOPIC_CHANNEL_NAME, topicChannelName)
                    putString(KEY_TOPIC_NAME, topicName)
                }
            }
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    private val messagesAdapter: MainAdapter by lazy {
        MainAdapter().apply {
            addDelegate(OutgoingMessageDelegate(::showDialog))
            addDelegate(IncomingMessageDelegate(::showDialog))
            addDelegate(DateDelegate())
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
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyArguments()
        setupAdapter()
        applyState()
        setTextChangeListener()
        setNavigationIconClickListener()

//TODO метод для отправки сообщения
        binding.sendOrAttach.setOnClickListener {
            val messageText = binding.newMessage.text?.trim().toString()

            binding.newMessage.text = null
        }
    }

    private fun applyArguments() {
        val topicChannelName = requireArguments().getString(KEY_TOPIC_CHANNEL_NAME) ?: EMPTY_STRING
        val topicName = requireArguments().getString(KEY_TOPIC_NAME) ?: EMPTY_STRING

        binding.toolbar.title =
            String.format(getString(R.string.hashtag_with_stream_name, topicChannelName))
        binding.topic.text = String.format(getString(R.string.topic_with_name), topicName)

        viewModel.loadMessages(topicChannelName, topicName)
    }

    private fun setupAdapter() {
        binding.chat.adapter = messagesAdapter

        val observer = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.chat.scrollToPosition(messagesAdapter.itemCount - 1)
            }
        }
        messagesAdapter.registerAdapterDataObserver(observer)
    }

    private fun applyState() {
        collectLifecycleFlow(viewModel.messages) { state ->
            when (state) {
                is ChatState.Content -> applyContent(state.data)

                ChatState.Loading -> applyLoading()

                ChatState.Error -> applyError()
            }
        }
    }

    private fun applyContent(data: List<Message>) {
        messagesAdapter.submitList(data.toDelegateItems(OWN_ID))

        binding.apply {
            chat.isVisible = true
            inputArea.isVisible = true

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            chat.isVisible = false
            inputArea.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun applyError() {
        showSnack()
    }

    private fun setTextChangeListener() {
        binding.newMessage.doOnTextChanged { text, _, _, _ ->
            binding.sendOrAttach.apply {
                if (text?.trim().isNullOrEmpty()) {
                    isClickable = false
                    setImageResource(R.drawable.icon_attach)
                } else {
                    isClickable = true
                    setImageResource(R.drawable.icon_send)
                }
            }
        }
    }

    private fun showSnack() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(view, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun setNavigationIconClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
    }

    private fun showDialog(messageId: Int) {
        val dialog = ChooseReactionFragment.newInstance(messageId)
        dialog.show(
            requireActivity().supportFragmentManager,
            ChooseReactionFragment.TAG
        )
        dialog.click = viewModel::addReactionToMessage
    }

    override fun onDestroy() {
        binding.chat.adapter = null
        _binding = null
        super.onDestroy()
    }
}