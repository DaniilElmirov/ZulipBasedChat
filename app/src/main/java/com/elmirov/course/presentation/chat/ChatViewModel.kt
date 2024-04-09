package com.elmirov.course.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.course.domain.entity.Message
import com.elmirov.course.domain.entity.Reaction
import com.elmirov.course.navigation.router.GlobalRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ViewModel() {

    companion object {
        private const val OWN_ID = 0
        private const val OTHER_ID = -1
    }

    init {
        loadMessages()
    }

    private val testData = mutableListOf(
        Message(
            id = 1,
            userId = OTHER_ID,
            date = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajkladsajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12, 0x1f600 to 12, 0x1f601 to 11)
        ),
        Message(
            id = 2,
            userId = OTHER_ID,
            date = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f601 to 11)
        ),
        Message(
            id = 3,
            userId = OTHER_ID,
            date = "12.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 4,
            userId = OTHER_ID,
            date = "14.02.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 5,
            userId = OTHER_ID,
            date = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = mapOf(0x1f600 to 12)
        ),
        Message(
            id = 6,
            userId = OTHER_ID,
            date = "14.12.2002",
            authorName = "AN",
            text = "ajdjklajdsjdsajklads",
            reactions = null
        ),
        Message(
            id = 7,
            userId = OWN_ID,
            date = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = mapOf(0x1f600 to 12, 0x1f600 to 12, 0x1f601 to 11),
        ),
        Message(
            id = 8,
            userId = OTHER_ID,
            date = "14.12.2002",
            authorName = "AN",
            text = "фывфывыфыфыфв",
            reactions = null
        ),
        Message(
            id = 9,
            userId = OWN_ID,
            date = "14.12.2002",
            authorName = "AN",
            text = "теусуцуываы",
            reactions = null
        ),
    )

    private val _messages = MutableStateFlow<ChatState>(ChatState.Loading)
    val messages = _messages.asStateFlow()

    fun sendMessage(message: Message) {
        testData.add(message)
        _messages.value = ChatState.Content(testData.toList())
    }

    fun addReactionToMessage(reaction: Reaction, messageId: Int) {
        val targetItem = testData.find {
            it.id == messageId
        }
        val newReactions =
            if (targetItem?.reactions == null)
                mapOf(reaction.emoji to reaction.count)
            else
                targetItem.reactions.plus(Pair(reaction.emoji, reaction.count))

        val targetIndex = testData.indexOf(targetItem)
        testData[targetIndex] = testData[targetIndex].copy(reactions = newReactions)

        _messages.value = ChatState.Content(testData.toList())
    }

    fun back() {
        globalRouter.back()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            delay(1000)
            _messages.value = ChatState.Content(testData.toList())
        }
    }
}