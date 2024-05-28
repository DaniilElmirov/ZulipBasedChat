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

    private companion object {
        const val DELETE_ALLOWED_IN_SECONDS = 9 * 60
        const val EDIT_ALLOWED_IN_SECONDS = 1440 * 60
        const val TRANSFER_ALLOWED_IN_SECONDS = 7 * 24 * 60 * 60
    }

    override fun Result.internal(event: ChatEvent.Internal): Any = when (event) {

        is ChatEvent.Internal.ChatLoadingError -> {
            state { copy(loading = false, loadingMore = false) }
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

        is ChatEvent.Internal.TopicsLoadingError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        is ChatEvent.Internal.AddReactionError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        ChatEvent.Internal.SendError -> {
            state { copy(loading = false) }
            effects { +ChatEffect.ShowError }
        }

        ChatEvent.Internal.DeleteSuccess,
        ChatEvent.Internal.EditSuccess,
        ChatEvent.Internal.ChangeTopicSuccess -> {
            commands { +ChatCommand.Load }
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

        is ChatEvent.Ui.OnDeleteClick -> {
            commands { +ChatCommand.Delete(event.messageId) }
        }

        is ChatEvent.Ui.OnIncomingMessageLongClick -> {
            effects {
                +ChatEffect.ShowMessageActionDialog(
                    messageId = event.messageId,
                    messageText = event.messageText,
                    deletable = false,
                    editable = false,
                    transferable = false,
                )
            }
        }

        is ChatEvent.Ui.OnOutgoingMessageLongClick -> {
            val currentTimestamp = System.currentTimeMillis().toIntSeconds()
            val timeDifference = currentTimestamp - event.timestamp

            effects {
                +ChatEffect.ShowMessageActionDialog(
                    messageId = event.messageId,
                    messageText = event.messageText,
                    deletable = timeDifference < DELETE_ALLOWED_IN_SECONDS,
                    editable = timeDifference < EDIT_ALLOWED_IN_SECONDS,
                    transferable = timeDifference < TRANSFER_ALLOWED_IN_SECONDS,
                )
            }
        }

        is ChatEvent.Ui.OnEditClick -> {
            commands { +ChatCommand.Edit(event.messageId, event.text) }
        }

        is ChatEvent.Ui.OnChangeTopicClick -> {
            commands { +ChatCommand.ChangeTopic(event.messageId, event.topicName) }
        }
    }

    private fun Long.toIntSeconds(): Int = (this / 1000).toInt()
}