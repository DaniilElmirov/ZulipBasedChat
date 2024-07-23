package com.elmirov.course.channels

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import com.elmirov.course.activity.ui.MainActivity
import com.elmirov.course.channels.mock.MockChannels.Companion.channels
import com.elmirov.course.channels.screen.SubscribedScreen
import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.mock.MockChat.Companion.messages
import com.elmirov.course.util.rule.AppTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubscribedChannelsFragmentTest : TestCase() {

    private companion object {
        private const val KEY_CHAT_INFO = "KEY_CHAT_INFO"

        private const val FIRST_CHANNEL = 0
    }

    @get:Rule
    val rule = AppTestRule<MainActivity>(getIntent())

    @Before
    fun setupMockServer() {
        rule.wiremockRule.start()

        rule.wiremockRule.channels {
            withSubscribed()
        }
        rule.wiremockRule.messages {
            withAll()
        }
    }

    @After
    fun stopMockServer() {
        rule.wiremockRule.stop()
    }

    @Test
    fun channelsIntegration() = run {

        SubscribedScreen {
            step("On first channel click") {
                channels.childAt<SubscribedScreen.ChannelScreenItem>(FIRST_CHANNEL) {
                    channel.click()
                }
            }

            step("Check arguments") {
                val expectedChatInfo = ChatInfo(
                    channelId = 1,
                    channelName = "MOCK: first subscribed channel",
                    topicName = ""
                )

                rule.activityScenarioRule.scenario.onActivity {
                    it.supportFragmentManager.fragments[0]?.arguments?.apply {
                        assertEquals(expectedChatInfo, getParcelable(KEY_CHAT_INFO))
                    }
                }
            }
        }
    }

    private fun getIntent(): Intent =
        Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
}