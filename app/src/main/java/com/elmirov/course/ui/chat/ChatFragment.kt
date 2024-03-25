package com.elmirov.course.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.elmirov.course.R
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.domain.Message
import com.elmirov.course.ui.chat.adapter.MessageAdapter

class ChatFragment : Fragment() {

    companion object {

        private const val OWN_ID = 0

        fun newInstance(): ChatFragment = ChatFragment()
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    private val messagesAdapter by lazy {
        MessageAdapter()
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

        binding.newMessage.doOnTextChanged { text, _, _, _ ->
            binding.sendOrAttach.apply {
                if (text.isNullOrEmpty())
                    setImageResource(R.drawable.icon_attach)
                else
                    setImageResource(R.drawable.icon_send)
            }
        }

        binding.sendOrAttach.setOnClickListener {
            val newMessage = binding.newMessage.text.toString()
            binding.newMessage.text = null
            messagesAdapter.messages += Message(
                userId = OWN_ID,
                authorName = "My message",
                text = newMessage,
            )
            binding.chat.apply {
                scrollToPosition(messagesAdapter.itemCount - 1)
            }
        }
    }

    override fun onDestroy() {
        binding.chat.adapter = null
        _binding = null
        super.onDestroy()
    }
}