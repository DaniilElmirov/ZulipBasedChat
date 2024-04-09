package com.elmirov.course.ui.chat

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
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.domain.entity.Message
import com.elmirov.course.presentation.ViewModelFactory
import com.elmirov.course.presentation.chat.ChatState
import com.elmirov.course.presentation.chat.ChatViewModel
import com.elmirov.course.ui.adapter.MainAdapter
import com.elmirov.course.ui.chat.delegate.date.DateDelegate
import com.elmirov.course.ui.chat.delegate.incoming.IncomingMessageDelegate
import com.elmirov.course.ui.chat.delegate.outgoing.OutgoingMessageDelegate
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toDelegateItems
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ChatFragment : Fragment() {

    companion object {

        private const val OWN_ID = 0

        private const val KEY_TOPIC_NAME = "KEY_TOPIC_NAME"

        fun newInstance(topicName: String): ChatFragment =
            ChatFragment().apply {
                arguments = Bundle().apply {
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

        parseArguments()
        setupAdapter()
        applyState()
        setTextChangeListener()
        setNavigationIconClickListener()

        var currentId = 10 //TODO поменять на данные с бэка

        binding.sendOrAttach.setOnClickListener {
            val messageText = binding.newMessage.text?.trim().toString()
            val newMessage = Message(
                id = currentId++,
                userId = if (currentId % 2 == 0) -1 else 0,
                date = getCurrentDate(),
                authorName = "I",
                text = messageText,
            )
            viewModel.sendMessage(newMessage)
            binding.newMessage.text = null
        }
    }

    private fun parseArguments() {
        val topicName = requireArguments().getString(KEY_TOPIC_NAME)

        binding.topic.text = String.format(getString(R.string.topic_with_name), topicName)
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

    private fun getCurrentDate(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return currentDateTime.format(formatter)
    }

    override fun onDestroy() {
        binding.chat.adapter = null
        _binding = null
        super.onDestroy()
    }
}