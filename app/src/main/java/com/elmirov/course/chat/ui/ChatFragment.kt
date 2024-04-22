package com.elmirov.course.chat.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.presentation.ChatEffect
import com.elmirov.course.chat.presentation.ChatEvent
import com.elmirov.course.chat.presentation.ChatState
import com.elmirov.course.chat.presentation.ChatStoreFactory
import com.elmirov.course.chat.ui.delegate.date.DateDelegate
import com.elmirov.course.chat.ui.delegate.incoming.IncomingMessageDelegate
import com.elmirov.course.chat.ui.delegate.outgoing.OutgoingMessageDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.util.toDelegateItems
import com.google.android.material.snackbar.Snackbar
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ChatFragment : ElmBaseFragment<ChatEffect, ChatState, ChatEvent>() {

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

    private val component by lazy {
        (requireActivity().application as CourseApplication).component
    }

    @Inject
    lateinit var chatStoreFactory: ChatStoreFactory

    override val store: Store<ChatEvent, ChatEffect, ChatState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        chatStoreFactory.create()
    }

    private val messagesAdapter: MainAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                OutgoingMessageDelegate(
                    openReactions = ::showDialog,
                    onReactionClick = { messageId, reaction, selected ->
                        store.accept(
                            ChatEvent.Ui.OnReactionClick(
                                channelName,
                                topicName,
                                messageId,
                                reaction,
                                selected
                            )
                        )
                    }
                )
            )
            addDelegate(
                IncomingMessageDelegate(
                    openReactions = ::showDialog,
                    onReactionClick = { messageId, reaction, selected ->
                        store.accept(
                            ChatEvent.Ui.OnReactionClick(
                                channelName,
                                topicName,
                                messageId,
                                reaction,
                                selected
                            )
                        )
                    }
                )
            )
            addDelegate(DateDelegate())
        }
    }

    private lateinit var channelName: String
    private lateinit var topicName: String

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
        setupListeners()
        setTextChangeListener()
        setNavigationIconClickListener()
    }

    override fun render(state: ChatState) {
        if (state.loading)
            applyLoading()

        state.content?.let {
            applyContent(it)
        }
    }

    override fun handleEffect(effect: ChatEffect): Unit = when (effect) {
        ChatEffect.ShowError -> applyError()
    }

    private fun applyArguments() {
        channelName = requireArguments().getString(KEY_TOPIC_CHANNEL_NAME) ?: EMPTY_STRING
        topicName = requireArguments().getString(KEY_TOPIC_NAME) ?: EMPTY_STRING

        binding.toolbar.title =
            String.format(getString(R.string.hashtag_with_stream_name, channelName))
        binding.topic.text = String.format(getString(R.string.topic_with_name), topicName)

        store.accept(ChatEvent.Ui.Init(channelName, topicName))
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

    private fun setupListeners() {
        binding.sendOrAttach.setOnClickListener {
            val messageText = binding.newMessage.text?.trim().toString()
            binding.newMessage.text = null

            sendMessage(messageText)
        }
    }

    private fun sendMessage(text: String) {
        store.accept(ChatEvent.Ui.OnSendMessageClick(channelName, topicName, text))
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
            store.accept(ChatEvent.Ui.OnBackClick)
        }
    }

    private fun showDialog(messageId: Int) {
        val dialog = ChooseReactionFragment.newInstance(messageId)
        dialog.show(
            requireActivity().supportFragmentManager,
            ChooseReactionFragment.TAG
        )

        dialog.click = { _, reaction, selected ->
            store.accept(
                ChatEvent.Ui.OnReactionClick(
                    channelName,
                    topicName,
                    messageId,
                    reaction,
                    selected
                )
            )
        }
    }

    override fun onDestroy() {
        binding.chat.adapter = null
        _binding = null
        super.onDestroy()
    }
}