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
        private const val OTHER_ID = -1

        fun newInstance(): ChatFragment = ChatFragment()
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    private val messagesAdapter by lazy {
        MessageAdapter()
    }

    private val testData = listOf(
        Message(
            id = 1,
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12, 0x1f600 to 12, 0x1f601 to 11)
        ),
        Message(
            id = 2,
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f601 to 11)
        ),
        Message(
            id = 3,
            userId = OTHER_ID,
            data = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 4,
            userId = OTHER_ID,
            data = "14.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 5,
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 6,
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = null
        ),
        Message(
            id = 7,
            userId = OWN_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = null
        ),
        Message(
            id = 8,
            userId = OTHER_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "фывфывыфыфыфв",
            reactions = null
        ),
        Message(
            id = 9,
            userId = OWN_ID,
            data = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = null
        ),
    )

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
        messagesAdapter.submitList(testData)

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
            binding.newMessage.text = null

            val currentList = messagesAdapter.currentList.toMutableList()
            currentList.add(newMessage)
            messagesAdapter.submitList(currentList)

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