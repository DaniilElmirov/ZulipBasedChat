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
import com.elmirov.course.presentation.chat.ChatViewModel
import com.elmirov.course.ui.adapter.MainAdapter
import com.elmirov.course.ui.chat.delegate.date.DateDelegate
import com.elmirov.course.ui.chat.delegate.incoming.IncomingMessageDelegate
import com.elmirov.course.ui.chat.delegate.outgoing.OutgoingMessageDelegate
import com.elmirov.course.util.collectLifecycleFlow
import com.elmirov.course.util.toDelegateItems
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatFragment : Fragment() {

    companion object {

        private const val OWN_ID = 0
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private val messagesAdapter: MainAdapter by lazy {
        MainAdapter().apply {
            addDelegate(OutgoingMessageDelegate(::showDialog))
            addDelegate(IncomingMessageDelegate(::showDialog))
            addDelegate(DateDelegate())
        }
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
            messagesAdapter.submitList(
                it.data.toDelegateItems(OWN_ID)
            )
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
                userId = if (currentId % 2 == 0) -1 else 0,
                date = getCurrentDate(),
                authorName = "I",
                text = messageText,
            )
            viewModel.sendMessage(newMessage)
            binding.newMessage.text = null
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