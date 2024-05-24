package com.elmirov.course.chat.util

import com.elmirov.course.channels.domain.entity.Topic
import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.chat.domain.entity.Message
import com.elmirov.course.chat.domain.entity.Reaction
import com.elmirov.course.chat.presentation.ChatCommand
import com.elmirov.course.chat.presentation.ChatEffect
import com.elmirov.course.chat.presentation.ChatEvent
import com.elmirov.course.chat.presentation.ChatState
import com.elmirov.course.core.result.domain.entity.ErrorType
import com.elmirov.course.core.result.domain.entity.Result
import com.elmirov.course.util.toTopicNames

object ChatPresentationTestData {

    const val CHANNEL_ID = 1
    const val CHANNEL_NAME = "CHANNEL_NAME"
    const val TOPIC_NAME = "TOPIC_NAME"

    const val MESSAGE_ID = 0

    val chatInfo = ChatInfo(CHANNEL_ID, CHANNEL_NAME, TOPIC_NAME)

    val reaction = Reaction(emojiName = "clown", emojiCode = "emojiCode")

    const val TEXT = "text"

    private val message = Message(
        id = MESSAGE_ID,
        timestamp = 1,
        topicName = TOPIC_NAME,
        authorId = 1,
        avatarUrl = null,
        authorName = "authorName",
        text = TEXT,
        reactions = emptyMap(),
    )
    private val emptyMessages = emptyList<Message>()
    private val messages = listOf(message, message, message)

    private val topic = Topic(CHANNEL_ID, TOPIC_NAME, 0)
    private val topics = listOf(topic, topic, topic)

    val state = ChatState()
    val stateLoading = ChatState(loading = true)
    val stateStopLoading = ChatState(loading = false)
    val stateContent = ChatState(loading = false, content = messages)
    val stateLoadingWithChatInfo = ChatState(loading = true, chatInfo = chatInfo)
    val stateLoadingMore = ChatState(loadingMore = true)
    val stateNoLoadingMore = ChatState(loadingMore = false)

    val effectShowError = ChatEffect.ShowError

    val commandLoadCashed = ChatCommand.LoadCached
    val commandLoad = ChatCommand.Load
    val commandAddReaction = ChatCommand.AddReaction(
        messageId = MESSAGE_ID,
        reaction = reaction,
        selected = false,
    )
    val commandSend = ChatCommand.Send(text = TEXT, topicName = TOPIC_NAME)
    val commandLoadMoreNext = ChatCommand.LoadMore(next = true)
    val commandLoadMorePrev = ChatCommand.LoadMore(next = false)
    val commandAddReactionSelected = ChatCommand.AddReaction(
        messageId = MESSAGE_ID,
        reaction = reaction,
        selected = true,
    )
    val commandAddReactionNoSelected = ChatCommand.AddReaction(
        messageId = MESSAGE_ID,
        reaction = reaction,
        selected = false,
    )
    val commandLoadTopics = ChatCommand.LoadTopics
    val commandLoadCachedTopics = ChatCommand.LoadCachedTopics

    val eventChatLoadingError = ChatEvent.Internal.ChatLoadingError
    val eventChatLoadingSuccessEmpty = ChatEvent.Internal.ChatLoadingSuccess(emptyMessages)
    val eventChatLoadingSuccess = ChatEvent.Internal.ChatLoadingSuccess(messages)
    val eventTopicLoadingSuccess = ChatEvent.Internal.TopicsLoadingSuccess(topics.toTopicNames())

    val eventInit = ChatEvent.Ui.Init(CHANNEL_ID, CHANNEL_NAME, TOPIC_NAME)
    val eventOnBackClick = ChatEvent.Ui.OnBackClick
    val eventOnReactionClick = ChatEvent.Ui.OnReactionClick(
        messageId = MESSAGE_ID,
        reaction = reaction,
        selected = false,
    )
    val eventOnSendMessageClick = ChatEvent.Ui.OnSendMessageClick(text = TEXT, topicName = TOPIC_NAME)
    val eventScrollDown = ChatEvent.Ui.ScrollDown
    val eventScrollUp = ChatEvent.Ui.ScrollUp
    val eventOnRefreshClick = ChatEvent.Ui.OnRefreshClick

    val resultSuccess = Result.Success(messages)
    val resultSuccessString = Result.Success(TEXT)
    val resultTopicsSuccess = Result.Success(topics)
    val resultError = Result.Error(ErrorType.UNKNOWN)
}