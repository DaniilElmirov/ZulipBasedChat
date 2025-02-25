package com.elmirov.course.chat.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmirov.course.CourseApplication
import com.elmirov.course.R
import com.elmirov.course.base.ElmBaseFragment
import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.presentation.ChatCommand
import com.elmirov.course.chat.presentation.ChatEffect
import com.elmirov.course.chat.presentation.ChatEvent
import com.elmirov.course.chat.presentation.ChatState
import com.elmirov.course.chat.ui.delegate.date.DateDelegate
import com.elmirov.course.chat.ui.delegate.incoming.IncomingMessageDelegate
import com.elmirov.course.chat.ui.delegate.outgoing.OutgoingMessageDelegate
import com.elmirov.course.chat.ui.delegate.topic.ChatTopicDelegate
import com.elmirov.course.core.adapter.MainAdapter
import com.elmirov.course.databinding.DialogChangeTopicBinding
import com.elmirov.course.databinding.DialogEditMessageBinding
import com.elmirov.course.databinding.DialogMessageActionBinding
import com.elmirov.course.databinding.FragmentChatBinding
import com.elmirov.course.util.getErrorSnackBar
import com.elmirov.course.util.showDialog
import com.elmirov.course.util.toDelegateItems
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import vivid.money.elmslie.android.renderer.elmStoreWithRenderer
import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class ChatFragment : ElmBaseFragment<ChatEffect, ChatState, ChatEvent>() {

    companion object {
        private const val OWN_ID = 709286

        private const val KEY_CHAT_INFO = "KEY_CHAT_INFO"

        private const val EMPTY_NAME = ""

        private const val LABEL = "LABEL"

        fun newInstance(chatInfo: ChatInfo): ChatFragment =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_CHAT_INFO, chatInfo)
                }
            }
    }

    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var chatInfo: ChatInfo

    private val component by lazy {
        (requireActivity().application as CourseApplication).component.chatComponentFactory()
            .create(chatInfo)
    }

    @Inject
    lateinit var chatStore: ElmStore<ChatEvent, ChatState, ChatEffect, ChatCommand>

    override val store: Store<ChatEvent, ChatEffect, ChatState> by elmStoreWithRenderer(
        elmRenderer = this
    ) {
        chatStore
    }

    private val messagesAdapter: MainAdapter by lazy {
        MainAdapter().apply {
            addDelegate(
                OutgoingMessageDelegate(
                    onMessageLongClick = { messageId, timestamp, messageText ->
                        store.accept(
                            ChatEvent.Ui.OnOutgoingMessageLongClick(
                                messageId,
                                timestamp,
                                messageText
                            )
                        )
                    },
                    onIconAddClick = ::showReactions,
                    onReactionClick = { messageId, reaction, selected ->
                        store.accept(
                            ChatEvent.Ui.OnReactionClick(
                                messageId = messageId,
                                reaction = reaction,
                                selected = selected,
                            )
                        )
                    }
                )
            )
            addDelegate(
                IncomingMessageDelegate(
                    onMessageLongClick = { messageId, timestamp, messageText ->
                        store.accept(
                            ChatEvent.Ui.OnIncomingMessageLongClick(
                                messageId,
                                timestamp,
                                messageText
                            )
                        )
                    },
                    onIconAddClick = ::showReactions,
                    onReactionClick = { messageId, reaction, selected ->
                        store.accept(
                            ChatEvent.Ui.OnReactionClick(
                                messageId = messageId,
                                reaction = reaction,
                                selected = selected,
                            )
                        )
                    }
                )
            )
            addDelegate(DateDelegate())
            addDelegate(
                ChatTopicDelegate(
                    onTopicClick = {
                        store.accept(
                            ChatEvent.Ui.OnTopicClick(
                                channelId = chatInfo.channelId,
                                channelName = chatInfo.channelName,
                                topicName = it,
                            )
                        )
                    }
                )
            )
        }
    }

    private var errorSnackBar: Snackbar? = null

    private lateinit var defaultTopicName: String

    override fun onAttach(context: Context) {
        applyArguments()
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

        initStatusBar()

        binding.chat.adapter = messagesAdapter
        store.accept(
            ChatEvent.Ui.Init(
                channelId = chatInfo.channelId,
                channelName = chatInfo.channelName,
                topicName = chatInfo.topicName,
            )
        )
        setupScrollListener()
        setTextChangeListener()
        setNavigationIconClickListener()
    }

    private fun initStatusBar() {
        requireActivity().window.statusBarColor = requireContext()
            .getColor(R.color.odd_topic)
    }

    @Suppress("DEPRECATION")
    private fun applyArguments() {
        val channelId = requireArguments().getParcelable<ChatInfo>(KEY_CHAT_INFO)?.channelId ?: 0
        val channelName =
            requireArguments().getParcelable<ChatInfo>(KEY_CHAT_INFO)?.channelName ?: EMPTY_NAME
        val topicName =
            requireArguments().getParcelable<ChatInfo>(KEY_CHAT_INFO)?.topicName ?: EMPTY_NAME

        chatInfo = ChatInfo(channelId, channelName, topicName)
    }

    override fun render(state: ChatState) {
        state.chatInfo?.let {
            setupToolbar(it.channelName, it.topicName)
        }

        if (state.loading)
            applyLoading()

        state.content?.let { messages ->
            val withTopics = state.chatInfo?.topicName?.isEmpty() ?: false
            applyContent(messages, withTopics)

            state.chatTopics?.let { names ->
                defaultTopicName = names[0]
                addTopicNames(names, withTopics)
            }

            setupClickListeners(withTopics)
        }
    }

    override fun handleEffect(effect: ChatEffect): Unit = when (effect) {
        ChatEffect.ShowError -> showErrorSnack()

        is ChatEffect.ShowMessageActionDialog -> showActionDialog(effect)
    }

    private fun showErrorSnack() {
        errorSnackBar = getErrorSnackBar(
            textResId = R.string.unknown_error,
            actionText = getString(R.string.try_again),
            actionListener = { store.accept(ChatEvent.Ui.OnRefreshClick) }
        )
        errorSnackBar?.anchorView = binding.snackAnchor
        errorSnackBar?.show()
    }

    private fun showActionDialog(effect: ChatEffect.ShowMessageActionDialog) {
        val actionView = DialogMessageActionBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(actionView.root)

        actionView.apply {
            delete.isVisible = effect.deletable
            edit.isVisible = effect.editable
            changeTopic.isVisible = effect.transferable

            addReaction.setOnClickListener {
                showReactions(effect.messageId)
                dialog.dismiss()
            }

            delete.setOnClickListener {
                store.accept(ChatEvent.Ui.OnDeleteClick(effect.messageId))
                dialog.dismiss()
            }

            edit.setOnClickListener {
                val dialogEditView = DialogEditMessageBinding.inflate(layoutInflater)
                dialogEditView.messageText.setText(effect.messageText)

                showDialog(
                    title = getString(R.string.edit_message_text),
                    layout = dialogEditView.root,
                    positiveButtonText = getString(R.string.edit),
                    onPositiveButtonClick = {
                        val newText = dialogEditView.messageText.text.toString()
                        store.accept(ChatEvent.Ui.OnEditClick(effect.messageId, newText))
                    }
                )
                dialog.dismiss()
            }

            changeTopic.setOnClickListener {
                val dialogChangeTopicView = DialogChangeTopicBinding.inflate(layoutInflater)
                showDialog(
                    title = getString(R.string.change_topic),
                    layout = dialogChangeTopicView.root,
                    positiveButtonText = getString(R.string.change),
                    onPositiveButtonClick = {
                        val newTopicName = dialogChangeTopicView.topicName.text?.trim().toString()
                            .ifEmpty { defaultTopicName }
                        store.accept(
                            ChatEvent.Ui.OnChangeTopicClick(
                                effect.messageId,
                                newTopicName
                            )
                        )
                    }
                )
                dialog.dismiss()
            }

            copy.setOnClickListener {
                val manager =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(LABEL, effect.messageText)
                manager.setPrimaryClip(clipData)

                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showReactions(messageId: Int) {
        val dialog = ChooseReactionFragment.newInstance(messageId)
        dialog.show(
            requireActivity().supportFragmentManager,
            ChooseReactionFragment.TAG
        )

        dialog.click = { _, reaction, selected ->
            store.accept(
                ChatEvent.Ui.OnReactionClick(
                    messageId = messageId,
                    reaction = reaction,
                    selected = selected,
                )
            )
        }
    }

    private fun setupToolbar(channelName: String, topicName: String) {
        binding.toolbar.title =
            String.format(
                getString(
                    R.string.hashtag_with_stream_name,
                    channelName
                )
            )

        if (topicName.isNotEmpty())
            binding.topic.text = String.format(getString(R.string.topic_with_name), topicName)
        else
            binding.topic.isVisible = false
    }

    private fun setupClickListeners(withTopics: Boolean) {
        binding.sendOrAttach.setOnClickListener {
            val messageText = binding.newMessage.text?.trim().toString()
            binding.newMessage.text = null

            if (messageText.isNotEmpty()) {
                if (withTopics) {
                    val selectedTopicName =
                        binding.topicName.text.trim().toString().ifEmpty { defaultTopicName }
                    store.accept(
                        ChatEvent.Ui.OnSendMessageClick(
                            messageText,
                            selectedTopicName
                        )
                    )
                } else
                    store.accept(ChatEvent.Ui.OnSendMessageClick(messageText, EMPTY_NAME))
            }
        }
    }

    private fun setupScrollListener() {
        val listener = object : RecyclerView.OnScrollListener() {

            private val layoutManager = binding.chat.layoutManager as LinearLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (firstVisiblePosition == RecyclerView.NO_POSITION)
                    firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

                var lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisiblePosition == RecyclerView.NO_POSITION)
                    lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

                if (dy > 0) {
                    val needLoadNext = (messagesAdapter.itemCount - lastVisiblePosition - 1) <= 5

                    if (needLoadNext)
                        store.accept(ChatEvent.Ui.ScrollDown)
                }

                if (dy < 0) {
                    val needLoadPrev = firstVisiblePosition <= 5

                    if (needLoadPrev)
                        store.accept(ChatEvent.Ui.ScrollUp)
                }
            }
        }

        binding.chat.addOnScrollListener(listener)
    }

    private fun applyContent(data: List<Message>, withTopics: Boolean) {
        messagesAdapter.submitList(data.toDelegateItems(OWN_ID, withTopics))

        binding.apply {
            chat.isVisible = true

            sendOrAttach.isVisible = true
            newMessage.isVisible = true
            topicName.isVisible = withTopics

            shimmer.isVisible = false
            shimmer.stopShimmer()
        }
    }

    private fun applyLoading() {
        binding.apply {
            chat.isVisible = false

            sendOrAttach.isVisible = false
            newMessage.isVisible = false
            topicName.isVisible = false

            shimmer.isVisible = true
            shimmer.startShimmer()
        }
    }

    private fun addTopicNames(topicNames: List<String>, withTopics: Boolean) {
        if (withTopics) {
            val adapter = ArrayAdapter(requireContext(), R.layout.topic_name_item, topicNames)
            binding.topicName.setAdapter(adapter)
            binding.topicName.hint = defaultTopicName
        }
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

    private fun setNavigationIconClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            store.accept(ChatEvent.Ui.OnBackClick)
        }
    }

    override fun onDestroyView() {
        binding.chat.adapter = null
        _binding = null
        errorSnackBar?.dismiss()
        errorSnackBar = null
        super.onDestroyView()
    }
}