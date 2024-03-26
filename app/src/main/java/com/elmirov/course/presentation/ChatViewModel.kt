package com.elmirov.course.presentation

import androidx.lifecycle.ViewModel
import com.elmirov.course.domain.Message
import com.elmirov.course.domain.Reaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {

    companion object {
        private const val OWN_ID = 0
        private const val OTHER_ID = -1
    }

    private val testData = mutableListOf(
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
            reactions = mapOf(0x1f600 to 12, 0x1f600 to 12, 0x1f601 to 11),
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

    private val _messages = MutableStateFlow(ChatState.Content(testData.toList()))
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
}