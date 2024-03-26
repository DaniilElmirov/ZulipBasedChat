package com.elmirov.course.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.domain.Message
import com.elmirov.course.presentation.ChatViewModel
import com.elmirov.course.ui.chat.adapter.MessageAdapter
import com.elmirov.course.util.collectLifecycleFlow

class ChatFragment : Fragment() {

    companion object {

        private const val OWN_ID = 0

        fun newInstance(): ChatFragment = ChatFragment()
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private val messagesAdapter by lazy {
        MessageAdapter(
            onAddIconClick = {
                val dialog = ChooseReactionFragment.newInstance(it)
                dialog.show(requireActivity().supportFragmentManager, ChooseReactionFragment.TAG)
                dialog.click = viewModel::addReactionToMessage
            },
            onMessageLongClick = {
                val dialog = ChooseReactionFragment.newInstance(it)
                dialog.show(requireActivity().supportFragmentManager, ChooseReactionFragment.TAG)
                dialog.click = viewModel::addReactionToMessage
            }
        )
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

        binding.chat.adapter = messagesAdapter
        collectLifecycleFlow(viewModel.messages) {
            messagesAdapter.submitList(it.data)
        }

        binding.newMessage.doOnTextChanged { text, _, _, _ ->
            binding.sendOrAttach.apply {
                if (text.isNullOrEmpty())
                    setImageResource(R.drawable.icon_attach)
                else
                    setImageResource(R.drawable.icon_send)
            }
        }

        var currentId = 10 //TODO поменять на данные с бэка

        binding.sendOrAttach.setOnClickListener {
            val messageText = binding.newMessage.text.toString()
            val newMessage = Message(
                id = currentId++,
                userId = OWN_ID,
                authorName = "I",
                text = messageText,
            )
            viewModel.sendMessage(newMessage)
            binding.newMessage.text = null
        }
    }

    override fun onDestroy() {
        binding.chat.adapter = null
        _binding = null
        super.onDestroy()
    }
}