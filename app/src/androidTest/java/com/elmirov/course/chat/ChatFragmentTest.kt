package com.elmirov.course.chat

import androidx.core.os.bundleOf
import com.elmirov.course.chat.mock.MockChat
import com.elmirov.course.chat.mock.MockChat.Companion.messages
import com.elmirov.course.chat.screen.ChatScreen
import com.elmirov.course.chat.ui.ChatFragment
import com.elmirov.course.util.rule.FragmentTestRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class ChatFragmentTest : TestCase() {

    private companion object {
        const val KEY_TOPIC_CHANNEL_NAME = "KEY_TOPIC_CHANNEL_NAME"
        const val KEY_TOPIC_NAME = "KEY_TOPIC_NAME"

        const val TEST_CHANNEL_NAME = "TEST_CHANNEL_NAME"
        const val TEST_TOPIC_NAME = "TEST_TOPIC_NAME"

        const val MY_MESSAGE = 3

        val testBundle = bundleOf(
            KEY_TOPIC_CHANNEL_NAME to TEST_CHANNEL_NAME,
            KEY_TOPIC_NAME to TEST_TOPIC_NAME
        )
    }

    @get:Rule
    val rule = FragmentTestRule(ChatFragment(), testBundle)

    @Test
    fun messagesHappyPath() = run {
        rule.wiremockRule.messages {
            withAll()
            withSend()
        }

        ChatScreen {
            step("Enter text in message field") {
                newMessage.replaceText("ssss")
            }
            step("Click on send button") {
                sendOrAttach.click()
            }
            step("Verify method") {
                verify(WireMock.getRequestedFor(MockChat.messagesUrlPattern))
            }
            step("New message visible") {
                messages.childAt<ChatScreen.MessageItem>(MY_MESSAGE) {
                    outgoing.isVisible()
                }
            }
        }
    }
}