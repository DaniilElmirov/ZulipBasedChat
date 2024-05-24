package com.elmirov.course.chat.presentation

import com.elmirov.course.chat.domain.entity.ChatInfo
import com.elmirov.course.navigation.router.GlobalRouter
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer
import javax.inject.Inject

class ChatReducer @Inject constructor(
    private val globalRouter: GlobalRouter,
) : ScreenDslReducer<
        ChatEvent,
        ChatEvent.Ui,
        ChatEvent.Internal,
        ChatState,
        ChatEffect,
        ChatCommand>(
    ChatEvent.Ui::class, ChatEvent.Internal::class
) {
    override fun Result.internal(event: ChatEvent.Internal): Any = when (event) {

        is ChatEvent.Internal.ChatLoadingError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        is ChatEvent.Internal.ChatLoadingSuccess -> {
            if (event.data.isEmpty())
                state { copy(loading = true) }
            else
                state { copy(loading = false, loadingMore = false, content = event.data) }
        }

        is ChatEvent.Internal.TopicsLoadingSuccess -> {
            if (event.data.isEmpty())
                state { copy(loading = true) }
            else
                state { copy(loading = false, chatTopics = event.data) }
        }
    }

    override fun Result.ui(event: ChatEvent.Ui): Any = when (event) {
        is ChatEvent.Ui.Init -> {
            commands { +ChatCommand.LoadCached }
            commands { +ChatCommand.LoadCachedTopics }
            state {
                copy(
                    loading = true,
                    chatInfo = ChatInfo(
                        channelId = event.channelId,
                        channelName = event.channelName,
                        topicName = event.topicName
                    ),
                )
            }
            commands { +ChatCommand.Load }
            commands { +ChatCommand.LoadTopics }
        }

        is ChatEvent.Ui.OnTopicClick -> {
            globalRouter.openChatTopic(
                ChatInfo(
                    channelId = event.channelId,
                    channelName = event.channelName,
                    topicName = event.topicName,
                )
            )
        }

        ChatEvent.Ui.OnBackClick -> globalRouter.back()

        is ChatEvent.Ui.OnReactionClick -> {
            commands {
                +ChatCommand.AddReaction(
                    messageId = event.messageId,
                    reaction = event.reaction,
                    selected = event.selected,
                )
            }
        }

        is ChatEvent.Ui.OnSendMessageClick -> {
            if (event.topicName.isEmpty()) {
                commands { +ChatCommand.Send(text = event.text, topicName = event.topicName) }
                commands { +ChatCommand.LoadTopics }
            } else
                commands { +ChatCommand.Send(text = event.text, topicName = event.topicName) }
        }

        is ChatEvent.Ui.ScrollDown -> {
            if (state.loadingMore)
                Unit
            else {
                state { copy(loadingMore = true) }
                commands { +ChatCommand.LoadMore(next = true) }
            }
        }

        ChatEvent.Ui.ScrollUp -> {
            if (state.loadingMore)
                Unit
            else {
                state { copy(loadingMore = true) }
                commands { +ChatCommand.LoadMore(next = false) }
            }
        }

        ChatEvent.Ui.OnRefreshClick -> {
            commands { +ChatCommand.Load }
        }
    }
}