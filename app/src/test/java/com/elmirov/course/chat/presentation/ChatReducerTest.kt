package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.util.ChatPresentationTestData
import com.elmirov.course.navigation.router.GlobalRouter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class ChatReducerTest {

    private val globalRouter: GlobalRouter = mock()
    private val reducer = ChatReducer(globalRouter)

    @Test
    fun `event ChatLoadingError AND state EXPECT stop loading AND effect ShowError`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventChatLoadingError, state)

            assertEquals(stateStopLoading, actual.state)
            assertTrue(actual.effects.contains(effectShowError))
        }
    }

    @Test
    fun `event ChatLoadingSuccess AND state EXPECT state with content`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventChatLoadingSuccess, state)

            assertEquals(stateContent, actual.state)
        }
    }

    @Test
    fun `event ChatLoadingSuccess with empty data AND state EXPECT state loading`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventChatLoadingSuccessEmpty, state)

            assertEquals(stateLoading, actual.state)
        }
    }

    @Test
    fun `event Init AND state EXPECT command LoadCached AND command Load AND state loading with ChatInfo`() {
        ChatPresentationTestData.apply {

            val targetCommands = listOf(commandLoadCashed, commandLoad)

            val actual = reducer.reduce(eventInit, state)

            assertTrue(actual.commands.containsAll(targetCommands))
            assertEquals(stateLoadingWithChatInfo, actual.state)
        }
    }

    @Test
    fun `event OnBackClick AND state EXPECT navigate back`() {
        ChatPresentationTestData.apply {

            reducer.reduce(eventOnBackClick, state)

            verify(globalRouter).back()
        }
    }

    @Test
    fun `event OnReactionClick AND state EXPECT command AddReaction`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventOnReactionClick, state)

            assertTrue(actual.commands.contains(commandAddReaction))
        }
    }

    @Test
    fun `event OnSendMessageClick AND state EXPECT command Send`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventOnSendMessageClick, state)

            assertTrue(actual.commands.contains(commandSend))
        }
    }

    @Test
    fun `event ScrollDown AND state NoLoadingMore EXPECT state LoadingMore AND command LoadMoreNext`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventScrollDown, stateNoLoadingMore)

            assertEquals(stateLoadingMore, actual.state)
            assertTrue(actual.commands.contains(commandLoadMoreNext))
        }
    }

    @Test
    fun `event ScrollDown AND state LoadingMore EXPECT state LoadingMore AND no effect AND no command`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventScrollDown, stateLoadingMore)

            assertEquals(stateLoadingMore, actual.state)
            assertTrue(actual.effects.isEmpty())
            assertTrue(actual.commands.isEmpty())
        }
    }

    @Test
    fun `event ScrollUp AND state NoLoadingMore EXPECT state LoadingMore AND command LoadMorePrev`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventScrollUp, stateNoLoadingMore)

            assertEquals(stateLoadingMore, actual.state)
            assertTrue(actual.commands.contains(commandLoadMorePrev))
        }
    }

    @Test
    fun `event ScrollUp AND state LoadingMore EXPECT state LoadingMore AND no effect AND no command`() {
        ChatPresentationTestData.apply {

            val actual = reducer.reduce(eventScrollUp, stateLoadingMore)

            assertEquals(stateLoadingMore, actual.state)
            assertTrue(actual.effects.isEmpty())
            assertTrue(actual.commands.isEmpty())
        }
    }
}