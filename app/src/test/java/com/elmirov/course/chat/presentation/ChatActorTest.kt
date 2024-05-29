package com.elmirov.course.chat.presentation

import app.cash.turbine.test
import com.elmirov.course.channels.domain.usecase.GetCachedChannelTopicsUseCase
import com.elmirov.course.channels.domain.usecase.GetChannelTopicsUseCase
import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.domain.usecase.AddReactionToMessageUseCase
import com.elmirov.course.chat.domain.usecase.ChangeTopicUseCase
import com.elmirov.course.chat.domain.usecase.DeleteMessageUseCase
import com.elmirov.course.chat.domain.usecase.EditMessageUseCase
import com.elmirov.course.chat.domain.usecase.GetCachedChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetChannelTopicMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetLastMessagesUseCase
import com.elmirov.course.chat.domain.usecase.GetUpdatedMessagesUseCase
import com.elmirov.course.chat.domain.usecase.LoadNextMessagesPageUseCase
import com.elmirov.course.chat.domain.usecase.LoadPrevMessagesPageUseCase
import com.elmirov.course.chat.domain.usecase.RemoveReactionUseCase
import com.elmirov.course.chat.domain.usecase.SendMessageToChannelTopicUseCase
import com.elmirov.course.chat.util.ChatPresentationTestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class ChatActorTest {

    private val chatInfo: ChatInfo = ChatPresentationTestData.chatInfo
    private val getChannelTopicMessagesUseCase: GetChannelTopicMessagesUseCase = mock()
    private val getCachedChannelTopicMessagesUseCase: GetCachedChannelTopicMessagesUseCase = mock()
    private val sendMessageToChannelTopicUseCase: SendMessageToChannelTopicUseCase = mock()
    private val addReactionToMessageUseCase: AddReactionToMessageUseCase = mock()
    private val removeReactionUseCase: RemoveReactionUseCase = mock()
    private val loadNextMessagesPageUseCase: LoadNextMessagesPageUseCase = mock()
    private val loadPrevMessagesPageUseCase: LoadPrevMessagesPageUseCase = mock()
    private val getUpdatedMessagesUseCase: GetUpdatedMessagesUseCase = mock()
    private val getLastMessagesUseCase: GetLastMessagesUseCase = mock()
    private val getChannelTopicsUseCase: GetChannelTopicsUseCase = mock()
    private val getCachedChannelTopicsUseCase: GetCachedChannelTopicsUseCase = mock()
    private val deleteMessageUseCase: DeleteMessageUseCase = mock()
    private val editMessageUseCase: EditMessageUseCase = mock()
    private val changeTopicUseCase: ChangeTopicUseCase = mock()

    private val actor = ChatActor(
        chatInfo,
        getChannelTopicMessagesUseCase,
        getCachedChannelTopicMessagesUseCase,
        sendMessageToChannelTopicUseCase,
        addReactionToMessageUseCase,
        removeReactionUseCase,
        loadNextMessagesPageUseCase,
        loadPrevMessagesPageUseCase,
        getUpdatedMessagesUseCase,
        getLastMessagesUseCase,
        getChannelTopicsUseCase,
        getCachedChannelTopicsUseCase,
        deleteMessageUseCase,
        editMessageUseCase,
        changeTopicUseCase,
    )

    @Test
    fun `command Load success EXPECT event ChatLoadingSuccess`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                getChannelTopicMessagesUseCase(
                    CHANNEL_NAME,
                    TOPIC_NAME
                )
            ) doReturn resultSuccess

            actor.execute(commandLoad).test {
                assertEquals(eventChatLoadingSuccess, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command Load error EXPECT event ChatLoadingError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(getChannelTopicMessagesUseCase(CHANNEL_NAME, TOPIC_NAME)) doReturn resultError

            actor.execute(commandLoad).test {
                assertEquals(eventChatLoadingError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadCashed success EXPECT event ChatLoadingSuccess`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                getCachedChannelTopicMessagesUseCase(
                    CHANNEL_NAME,
                    TOPIC_NAME
                )
            ) doReturn resultSuccess

            actor.execute(commandLoadCashed).test {
                assertEquals(eventChatLoadingSuccess, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadCashed error EXPECT event ChatLoadingError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                getCachedChannelTopicMessagesUseCase(
                    CHANNEL_NAME,
                    TOPIC_NAME
                )
            ) doReturn resultError

            actor.execute(commandLoadCashed).test {
                assertEquals(eventChatLoadingError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadMoreNext success EXPECT event ChatLoadingSuccess`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                loadNextMessagesPageUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    id = MESSAGE_ID
                )
            ) doReturn resultSuccess

            actor.execute(commandLoadMoreNext).test {
                assertEquals(eventChatLoadingSuccess, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadMoreNext error EXPECT event ChatLoadingError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                loadNextMessagesPageUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    id = MESSAGE_ID
                )
            ) doReturn resultError

            actor.execute(commandLoadMoreNext).test {
                assertEquals(eventChatLoadingError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadMorePrev success EXPECT event ChatLoadingSuccess`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                loadPrevMessagesPageUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    id = MESSAGE_ID
                )
            ) doReturn resultSuccess

            actor.execute(commandLoadMorePrev).test {
                assertEquals(eventChatLoadingSuccess, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command LoadMorePrev error EXPECT event ChatLoadingError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                loadPrevMessagesPageUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    id = MESSAGE_ID
                )
            ) doReturn resultError

            actor.execute(commandLoadMorePrev).test {
                assertEquals(eventChatLoadingError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command Send success AND get lasts success EXPECT event ChatLoadingSuccess`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                getLastMessagesUseCase(
                    CHANNEL_NAME,
                    TOPIC_NAME
                )
            ) doReturn resultSuccess
            whenever(
                sendMessageToChannelTopicUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    text = TEXT
                )
            ) doReturn resultSuccessString

            actor.execute(commandSend).test {
                assertEquals(eventChatLoadingSuccess, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command Send success AND get lasts error EXPECT event ChatLoadingError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                getLastMessagesUseCase(
                    CHANNEL_NAME,
                    TOPIC_NAME
                )
            ) doReturn resultError
            whenever(
                sendMessageToChannelTopicUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    text = TEXT
                )
            ) doReturn resultSuccessString

            actor.execute(commandSend).test {
                assertEquals(eventChatLoadingError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command Send error EXPECT event SendError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                sendMessageToChannelTopicUseCase(
                    channelName = CHANNEL_NAME,
                    topicName = TOPIC_NAME,
                    text = TEXT
                )
            ) doReturn resultError

            actor.execute(commandSend).test {
                assertEquals(eventSendError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command AddReactionSelected error EXPECT event AddReactionError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                removeReactionUseCase(
                    messageId = MESSAGE_ID,
                    emojiName = reaction.emojiName,
                    emojiCode = reaction.emojiCode
                )
            ) doReturn resultError

            actor.execute(commandAddReactionSelected).test {
                assertEquals(eventAddReactionError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command AddReactionSelected success and get updated error EXPECT event ChatLoadingError`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(
                    removeReactionUseCase(
                        messageId = MESSAGE_ID,
                        emojiName = reaction.emojiName,
                        emojiCode = reaction.emojiCode
                    )
                ) doReturn resultSuccessString
                whenever(
                    getUpdatedMessagesUseCase(
                        channelName = CHANNEL_NAME,
                        topicName = TOPIC_NAME,
                        id = MESSAGE_ID,
                    )
                ) doReturn resultError

                actor.execute(commandAddReactionSelected).test {
                    assertEquals(eventChatLoadingError, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command AddReactionSelected success and get updated success EXPECT event ChatLoadingSuccess`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(
                    removeReactionUseCase(
                        messageId = MESSAGE_ID,
                        emojiName = reaction.emojiName,
                        emojiCode = reaction.emojiCode
                    )
                ) doReturn resultSuccessString
                whenever(
                    getUpdatedMessagesUseCase(
                        channelName = CHANNEL_NAME,
                        topicName = TOPIC_NAME,
                        id = MESSAGE_ID,
                    )
                ) doReturn resultSuccess

                actor.execute(commandAddReactionSelected).test {
                    assertEquals(eventChatLoadingSuccess, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command AddReactionNoSelected error EXPECT event AddReactionError`() = runTest {
        ChatPresentationTestData.apply {

            whenever(
                addReactionToMessageUseCase(
                    messageId = MESSAGE_ID,
                    emojiName = reaction.emojiName,
                    emojiCode = reaction.emojiCode
                )
            ) doReturn resultError

            actor.execute(commandAddReactionNoSelected).test {
                assertEquals(eventAddReactionError, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `command AddReactionNoSelected success and get updated error EXPECT event ChatLoadingError`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(
                    addReactionToMessageUseCase(
                        messageId = MESSAGE_ID,
                        emojiName = reaction.emojiName,
                        emojiCode = reaction.emojiCode
                    )
                ) doReturn resultSuccessString
                whenever(
                    getUpdatedMessagesUseCase(
                        channelName = CHANNEL_NAME,
                        topicName = TOPIC_NAME,
                        id = MESSAGE_ID,
                    )
                ) doReturn resultError

                actor.execute(commandAddReactionNoSelected).test {
                    assertEquals(eventChatLoadingError, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command AddReactionNoSelected success and get updated success EXPECT event ChatLoadingSuccess`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(
                    addReactionToMessageUseCase(
                        messageId = MESSAGE_ID,
                        emojiName = reaction.emojiName,
                        emojiCode = reaction.emojiCode
                    )
                ) doReturn resultSuccessString
                whenever(
                    getUpdatedMessagesUseCase(
                        channelName = CHANNEL_NAME,
                        topicName = TOPIC_NAME,
                        id = MESSAGE_ID,
                    )
                ) doReturn resultSuccess

                actor.execute(commandAddReactionNoSelected).test {
                    assertEquals(eventChatLoadingSuccess, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command LoadTopics success EXPECT event TopicsLoadingSuccess`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(getChannelTopicsUseCase(CHANNEL_ID)) doReturn resultTopicsSuccess

                actor.execute(commandLoadTopics).test {
                    assertEquals(eventTopicLoadingSuccess, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command LoadTopics error EXPECT event TopicsLoadingError`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(getChannelTopicsUseCase(CHANNEL_ID)) doReturn resultError

                actor.execute(commandLoadTopics).test {
                    assertEquals(eventTopicsLoadingError, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command LoadCachedTopics success EXPECT event TopicsLoadingSuccess`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(getCachedChannelTopicsUseCase(CHANNEL_ID)) doReturn resultTopicsSuccess

                actor.execute(commandLoadCachedTopics).test {
                    assertEquals(eventTopicLoadingSuccess, awaitItem())
                    awaitComplete()
                }
            }
        }

    @Test
    fun `command LoadCachedTopics error EXPECT event ChatLoadingError`() =
        runTest {
            ChatPresentationTestData.apply {

                whenever(getCachedChannelTopicsUseCase(CHANNEL_ID)) doReturn resultTopicsSuccess

                actor.execute(commandLoadCachedTopics).test {
                    assertEquals(eventTopicLoadingSuccess, awaitItem())
                    awaitComplete()
                }
            }
        }
}